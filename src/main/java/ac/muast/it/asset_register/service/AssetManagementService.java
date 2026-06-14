// service/AssetManagementService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public AssetResponse assignAsset(Long id, AssignAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.ASSIGN);

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        // Create new assignment
        AssetAssignmentHistory assignment = AssetAssignmentHistory.builder()
            .asset(asset)
            .user(user)
            .roleAtAssignment(request.getRoleAtAssignment())
            .notes(request.getNotes())
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        assignmentHistoryRepository.save(assignment);

        // Update status
        closeCurrentStatus(asset, now);

        AssetStatus assignedStatus = assetStatusService.getByName(AssetStatus.ASSIGNED);
        createStatusHistory(asset, assignedStatus, "Assigned to " + user.getUsername(), now);
        asset.setCurrentStatus(assignedStatus);

        log.info("Asset {} assigned to {} as {}",
            asset.getCode(), user.getUsername(), request.getRoleAtAssignment());
        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse transferAsset(Long id, TransferAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.TRANSFER);

        Office office = officeRepository.findById(request.getOfficeId())
            .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        LocalDateTime now = LocalDateTime.now();

        // Close current location
        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Create new location
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
    public AssetResponse checkinAsset(Long asset_id, CheckinAssetRequest request) {
        Asset asset = assetRepository.findById(asset_id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + asset_id));

        Office returnOffice = officeRepository.findById(request.getReturnOfficeId())
            .orElseThrow(() -> new ResourceNotFoundException("Office not found: " + request.getReturnOfficeId()));

        validateAction(asset, AssetStatus.Action.CHECKIN);

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        // Close current location
        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Create new location
        AssetLocationHistory location = AssetLocationHistory.builder()
            .asset(asset)
            .office(returnOffice)
            .notes("Transferred to " + returnOffice.getName())
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        locationHistoryRepository.save(location);

        // Update status
        closeCurrentStatus(asset, now);
        AssetStatus assignedStatus = assetStatusService.getByName(AssetStatus.AVAILABLE);
        createStatusHistory(
            asset, 
            assignedStatus, 
            request.getNotes() != null ? request.getNotes() : "Checked in", 
            now
        );
        asset.setCurrentStatus(assignedStatus);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse markForRepair(Long id, MarkForRepairRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.REPAIR);

        LocalDateTime now = LocalDateTime.now();

        closeCurrentStatus(asset, now);
        AssetStatus assignedStatus = assetStatusService.getByName(AssetStatus.IN_REPAIR);
        createStatusHistory(
            asset, 
            assignedStatus, 
            request.getNotes() != null ? request.getNotes() : "Asset " + id + "marked for repair", 
            now
        );
        asset.setCurrentStatus(assignedStatus);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse markAsLost(Long id, String notes) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.LOST);

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(id, AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                current.setNotes(notes);
                assignmentHistoryRepository.save(current);
            });

        // Close current location
        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Update status
        closeCurrentStatus(asset, now);
        AssetStatus assignedStatus = assetStatusService.getByName(AssetStatus.LOST);
        createStatusHistory(asset, assignedStatus, notes, now);
        asset.setCurrentStatus(assignedStatus);
        asset.setNotes(notes);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse decommissionAsset(Long id, String notes) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.DECOMMISSION);

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        // Close current location
        locationHistoryRepository.findCurrentByAssetId(asset.getId(), AssetHistory.MAX_VALID_TO)
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Update status
        closeCurrentStatus(asset, now);
        AssetStatus assignedStatus = assetStatusService.getByName(AssetStatus.DECOMMISSIONED);
        createStatusHistory(asset, assignedStatus, notes, now);
        asset.setCurrentStatus(assignedStatus);
        asset.setNotes(notes);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse recoverAsset(Long id, RecoverAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        AssetStatus currentStatus = asset.getCurrentStatus();
        AssetStatus lostStatus = assetStatusService.getByName(AssetStatus.LOST);
        AssetStatus decommissionedStatus = assetStatusService.getByName(AssetStatus.DECOMMISSIONED);
        if (currentStatus.getId().equals(lostStatus.getId())) {   
            validateAction(asset, AssetStatus.Action.RECOVER);
        } else if (currentStatus.getId().equals(decommissionedStatus.getId())) {
            validateAction(asset, AssetStatus.Action.RECOVER);
        } else {
            throw new IllegalStateException("Recovery only available for LOST or DECOMMISSIONED assets");
        }

        LocalDateTime now = LocalDateTime.now();

        closeCurrentStatus(asset, now);
        createStatusHistory(asset, assetStatusService.getById(request.getTargetStatusId()), "Recovered from " + currentStatus.getName(), now);
        asset.setCurrentStatus(assetStatusService.getById(request.getTargetStatusId()));
        asset.setNotes((asset.getNotes() != null ? asset.getNotes() + " | " : "") + "Recovered from " + currentStatus.getName() + " on " + now);

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