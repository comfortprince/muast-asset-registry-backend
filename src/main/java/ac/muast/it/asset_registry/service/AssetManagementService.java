// service/AssetManagementService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.request.AssignAssetRequest;
import ac.muast.it.asset_registry.dto.request.TransferAssetRequest;
import ac.muast.it.asset_registry.dto.response.AssetResponse;
import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.model.*;
import ac.muast.it.asset_registry.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AssetResponse assignAsset(Long id, AssignAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.ASSIGN);

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId())
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
        createStatusHistory(asset, AssetStatus.ASSIGNED, "Assigned to " + user.getUsername(), now);
        asset.setCurrentStatus(AssetStatus.ASSIGNED);

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
        locationHistoryRepository.findCurrentByAssetId(asset.getId())
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Create new location
        AssetLocationHistory location = AssetLocationHistory.builder()
            .asset(asset)
            .office(office)
            .validFrom(now)
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        locationHistoryRepository.save(location);

        log.info("Asset {} transferred to office {}", asset.getCode(), office.getName());
        return assetService.mapToResponse(asset);
    }

    @Transactional
    public AssetResponse checkinAsset(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.CHECKIN);

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId())
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        // Update status
        closeCurrentStatus(asset, now);
        createStatusHistory(asset, AssetStatus.AVAILABLE, "Checked in", now);
        asset.setCurrentStatus(AssetStatus.AVAILABLE);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse sendForRepair(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.REPAIR);

        LocalDateTime now = LocalDateTime.now();

        closeCurrentStatus(asset, now);
        createStatusHistory(asset, AssetStatus.IN_REPAIR, "Sent for repair", now);
        asset.setCurrentStatus(AssetStatus.IN_REPAIR);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse markAsLost(Long id, String notes) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        validateAction(asset, AssetStatus.Action.LOST);

        LocalDateTime now = LocalDateTime.now();

        // Close current assignment
        assignmentHistoryRepository.findCurrentByAssetId(id)
            .ifPresent(current -> {
                current.setValidTo(now);
                current.setNotes(notes);
                assignmentHistoryRepository.save(current);
            });

        // Update status
        closeCurrentStatus(asset, now);
        createStatusHistory(asset, AssetStatus.LOST, notes, now);
        asset.setCurrentStatus(AssetStatus.LOST);
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
        assignmentHistoryRepository.findCurrentByAssetId(asset.getId())
            .ifPresent(current -> {
                current.setValidTo(now);
                assignmentHistoryRepository.save(current);
            });

        // Close current location
        locationHistoryRepository.findCurrentByAssetId(asset.getId())
            .ifPresent(current -> {
                current.setValidTo(now);
                locationHistoryRepository.save(current);
            });

        // Update status
        closeCurrentStatus(asset, now);
        createStatusHistory(asset, AssetStatus.DECOMMISSIONED, notes, now);
        asset.setCurrentStatus(AssetStatus.DECOMMISSIONED);
        asset.setNotes(notes);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public AssetResponse recoverAsset(Long id, AssetStatus targetStatus) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        AssetStatus current = asset.getCurrentStatus();
        if (current != AssetStatus.LOST && current != AssetStatus.DECOMMISSIONED) {
            throw new IllegalStateException("Recovery only available for LOST or DECOMMISSIONED assets");
        }

        LocalDateTime now = LocalDateTime.now();

        closeCurrentStatus(asset, now);
        createStatusHistory(asset, targetStatus, "Recovered from " + current, now);
        asset.setCurrentStatus(targetStatus);
        asset.setNotes(asset.getNotes() + " | Recovered from " + current + " on " + now);

        return assetService.mapToResponse(assetRepository.save(asset));
    }

    // =============================================
    // HELPERS
    // =============================================

    private void validateAction(Asset asset, String action) {
        if (!asset.getCurrentStatus().canPerform(action)) {
            throw new IllegalStateException(
                "Cannot " + action + " asset with status " + asset.getCurrentStatus());
        }
    }

    private void closeCurrentStatus(Asset asset, LocalDateTime now) {
        statusHistoryRepository.findCurrentByAssetId(asset.getId())
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