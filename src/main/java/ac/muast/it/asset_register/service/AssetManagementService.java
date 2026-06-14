// service/AssetManagementService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.AssignAssetRequest;
import ac.muast.it.asset_register.dto.request.CheckinAssetRequest;
import ac.muast.it.asset_register.dto.request.MarkForRepairRequest;
import ac.muast.it.asset_register.dto.request.RecoverAssetRequest;
import ac.muast.it.asset_register.dto.request.TransferAssetRequest;
import ac.muast.it.asset_register.dto.response.AssetResponse;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.*;
import ac.muast.it.asset_register.repository.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetManagementService {

    private final AssetRepository assetRepository;
    private final AssetAssignmentHistoryRepository assignmentHistoryRepository;
    private final AssetLocationHistoryRepository locationHistoryRepository;
    private final AssetStatusHistoryRepository statusHistoryRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final AssetService assetService;
    private final AssetStatusService assetStatusService;

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse assignAsset(Long id, AssignAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.ASSIGN);

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        LocalDateTime now = LocalDateTime.now();

        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        AssetAssignmentHistory assignment = AssetAssignmentHistory.builder()
            .asset(asset)
            .user(user)
            .roleAtAssignment(request.getRoleAtAssignment())
            .notes(request.getNotes())
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        assignmentHistoryRepository.save(assignment);

        closeCurrentStatus(asset, now);
        AssetStatus assignedStatus = assetStatusService.getByName("ASSIGNED");
        createStatusHistory(asset, assignedStatus, "Assigned to " + user.getUsername(), now);
        asset.setCurrentStatus(assignedStatus);

        log.info("Asset {} assigned to {} as {}",
            asset.getCode(), user.getUsername(), request.getRoleAtAssignment());
        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse transferAsset(Long id, TransferAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.TRANSFER);

        Office office = officeRepository.findById(request.getOfficeId())
            .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        LocalDateTime now = LocalDateTime.now();

        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        AssetLocationHistory location = AssetLocationHistory.builder()
            .asset(asset)
            .office(office)
            .notes(request.getNotes() != null ? request.getNotes() : "Transferred to " + office.getName())
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        locationHistoryRepository.save(location);

        log.info("Asset {} transferred to office {}", asset.getCode(), office.getName());
        return assetService.mapToResponse(asset);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse checkinAsset(Long assetId, CheckinAssetRequest request) {
        Asset asset = assetRepository.findById(assetId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetId));

        Office returnOffice = officeRepository.findById(request.getReturnOfficeId())
            .orElseThrow(() -> new ResourceNotFoundException("Office not found: " + request.getReturnOfficeId()));

        validateAction(asset, AssetStatus.Action.CHECKIN);

        LocalDateTime now = LocalDateTime.now();

        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        AssetLocationHistory location = AssetLocationHistory.builder()
            .asset(asset)
            .office(returnOffice)
            .notes("Checked in to " + returnOffice.getName())
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        locationHistoryRepository.save(location);

        closeCurrentStatus(asset, now);
        AssetStatus availableStatus = assetStatusService.getByName("AVAILABLE");
        createStatusHistory(asset, availableStatus,
            request.getNotes() != null ? request.getNotes() : "Checked in", now);
        asset.setCurrentStatus(availableStatus);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse markForRepair(Long id, MarkForRepairRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.REPAIR);

        LocalDateTime now = LocalDateTime.now();

        closeCurrentStatus(asset, now);
        AssetStatus repairStatus = assetStatusService.getByName("IN_REPAIR");
        createStatusHistory(asset, repairStatus,
            request.getNotes() != null ? request.getNotes() : "Marked for repair", now);
        asset.setCurrentStatus(repairStatus);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse markAsLost(Long id, String notes) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.LOST);

        LocalDateTime now = LocalDateTime.now();

        assignmentHistoryRepository.findCurrentByAssetId(id, AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                current.setNotes(notes);
                assignmentHistoryRepository.save(current);
            });

        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        closeCurrentStatus(asset, now);
        AssetStatus lostStatus = assetStatusService.getByName("LOST");
        createStatusHistory(asset, lostStatus, notes, now);
        asset.setCurrentStatus(lostStatus);
        asset.setNotes(notes);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse decommissionAsset(Long id, String notes) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.DECOMMISSION);

        LocalDateTime now = LocalDateTime.now();

        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        closeCurrentStatus(asset, now);
        AssetStatus decommissionedStatus = assetStatusService.getByName("DECOMMISSIONED");
        createStatusHistory(asset, decommissionedStatus, notes, now);
        asset.setCurrentStatus(decommissionedStatus);
        asset.setNotes(notes);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public AssetResponse recoverAsset(Long id, RecoverAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        String currentName = asset.getCurrentStatus().getName();
        if (!AssetStatus.LOST.equals(currentName) && !AssetStatus.DECOMMISSIONED.equals(currentName)) {
            throw new IllegalStateException("Recovery only available for LOST or DECOMMISSIONED assets");
        }

        validateAction(asset, AssetStatus.Action.RECOVER);

        LocalDateTime now = LocalDateTime.now();
        AssetStatus targetStatus = assetStatusService.getById(request.getTargetStatusId());

        closeCurrentStatus(asset, now);
        createStatusHistory(asset, targetStatus, "Recovered from " + currentName, now);
        asset.setCurrentStatus(targetStatus);
        asset.setNotes((asset.getNotes() != null ? asset.getNotes() + " | " : "")
            + "Recovered from " + currentName + " on " + now);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    // =============================================
    // HELPERS
    // =============================================

    private void validateAction(Asset asset, String action) {
        if (!asset.getCurrentStatus().canPerformAction(action)) {
            throw new IllegalStateException(
                "Cannot " + action + " asset with status " + asset.getCurrentStatus().getName());
        }
    }

    private void closeCurrentStatus(Asset asset, LocalDateTime now) {
        statusHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                statusHistoryRepository.save(current);
            });
    }

    private void createStatusHistory(Asset asset, AssetStatus status, String reason, LocalDateTime validFrom) {
        AssetStatusHistory history = AssetStatusHistory.builder()
            .asset(asset)
            .status(status)
            .reason(reason)
            .validFrom(validFrom)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        statusHistoryRepository.save(history);
    }
}