// service/AssetService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.request.AssignAssetRequest;
import ac.muast.it.asset_registry.dto.request.TransferAssetRequest;
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
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetAssignmentRepository assetAssignmentRepository;
    private final AssetLocationRepository assetLocationRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    @Transactional
    public Asset assignAsset(String assetCode, AssignAssetRequest request) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.ASSIGN);

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUsername()));

        assetAssignmentRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                current.setReturnedAt(LocalDateTime.now());
                assetAssignmentRepository.save(current);
            });

        AssetAssignment assignment = AssetAssignment.builder()
            .asset(asset)
            .user(user)
            .roleAtAssignment(request.getRoleAtAssignment())
            .isCurrent(true)
            .assignedAt(LocalDateTime.now())
            .notes(request.getNotes())
            .build();
        assetAssignmentRepository.save(assignment);

        asset.setStatus(AssetStatus.ASSIGNED);
        asset = assetRepository.save(asset);

        log.info("Asset {} assigned to {} as {}", 
            assetCode, request.getUsername(), request.getRoleAtAssignment());
        return asset;
    }

    @Transactional
    public Asset transferAsset(String assetCode, TransferAssetRequest request) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.TRANSFER);

        Office office = officeRepository.findById(request.getOfficeId())
            .orElseThrow(() -> new ResourceNotFoundException("Office not found"));

        assetLocationRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                assetLocationRepository.save(current);
            });

        AssetLocation location = AssetLocation.builder()
            .asset(asset)
            .office(office)
            .isCurrent(true)
            .assignedAt(LocalDateTime.now())
            .build();
        assetLocationRepository.save(location);

        log.info("Asset {} transferred to office {}", assetCode, office.getDisplayName());
        return asset;
    }

    @Transactional
    public Asset checkinAsset(String assetCode) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.CHECKIN);
        
        assetAssignmentRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                current.setReturnedAt(LocalDateTime.now());
                assetAssignmentRepository.save(current);
            });

        asset.setStatus(AssetStatus.AVAILABLE);
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset sendForRepair(String assetCode) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.REPAIR);

        asset.setStatus(AssetStatus.IN_REPAIR);
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset markAsLost(String assetCode, String notes) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.LOST);

        assetAssignmentRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                current.setReturnedAt(LocalDateTime.now());
                current.setNotes(notes);
                assetAssignmentRepository.save(current);
            });

        asset.setStatus(AssetStatus.LOST);
        asset.setNotes(notes);
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset decommissionAsset(String assetCode, String notes) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        validateAction(asset, AssetStatus.Action.DECOMMISSION);

        assetAssignmentRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                current.setReturnedAt(LocalDateTime.now());
                assetAssignmentRepository.save(current);
            });

        // Close current location
        assetLocationRepository.findByAssetIdAndIsCurrentTrue(asset.getId())
            .ifPresent(current -> {
                current.setIsCurrent(false);
                assetLocationRepository.save(current);
            });

        asset.setStatus(AssetStatus.DECOMMISSIONED);
        asset.setNotes(notes);
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset recoverAsset(String assetCode, AssetStatus targetStatus) {
        Asset asset = assetRepository.findByAssetCode(assetCode)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetCode));

        // Only allow recovery from terminal states
        if (asset.getStatus() != AssetStatus.LOST && asset.getStatus() != AssetStatus.DECOMMISSIONED) {
            throw new IllegalStateException("Recovery only available for LOST or DECOMMISSIONED assets");
        }

        asset.setStatus(targetStatus);
        asset.setNotes(asset.getNotes() + " | Recovered from " + asset.getStatus() + " on " + LocalDateTime.now());
        return assetRepository.save(asset);
    }

    private void validateAction(Asset asset, String action) {
        if (!asset.getStatus().canPerform(action)) {
            throw new IllegalStateException(
                "Cannot " + action + " asset with status " + asset.getStatus());
        }
    }
}