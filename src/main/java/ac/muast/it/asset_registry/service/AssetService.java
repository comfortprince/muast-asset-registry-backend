// service/AssetService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.request.CreateAssetRequest;
import ac.muast.it.asset_registry.dto.response.*;
import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.model.*;
import ac.muast.it.asset_registry.repository.*;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final GrvEntryRepository grvEntryRepository;
    private final AssetStatusHistoryRepository statusHistoryRepository;

    @Transactional
    public AssetResponse createAsset(CreateAssetRequest request) {
        AssetType assetType = assetTypeRepository.findById(request.getAssetTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset type not found: " + request.getAssetTypeId()));

        GrvEntry grvEntry = null;
        if (request.getGrvEntryId() != null) {
            grvEntry = grvEntryRepository.findById(request.getGrvEntryId())
                .orElseThrow(() -> new ResourceNotFoundException("GRV entry not found: " + request.getGrvEntryId()));
        }

        Asset asset = Asset.builder()
            .code(request.getCode())
            .assetType(assetType)
            .grvEntry(grvEntry)
            .brand(request.getBrand())
            .serialNumber(request.getSerialNumber())
            .currentStatus(AssetStatus.AVAILABLE)
            .purchaseDate(request.getPurchaseDate())
            .purchaseCost(request.getPurchaseCost())
            .specs(request.getSpecs())
            .notes(request.getNotes())
            .build();

        asset = assetRepository.save(asset);

        // Create initial status history
        AssetStatusHistory statusHistory = AssetStatusHistory.builder()
            .asset(asset)
            .status(AssetStatus.AVAILABLE)
            .reason("Asset created")
            .validFrom(java.time.LocalDateTime.now())
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        statusHistoryRepository.save(statusHistory);

        return mapToResponse(asset);
    }

    @Transactional(readOnly = true)
    public Page<AssetResponse> getAllAssets(Pageable pageable) {
        return assetRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public AssetResponse getAssetById(Long id) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));
        return mapToResponse(asset);
    }

    @Transactional
    public AssetResponse updateAsset(Long id, CreateAssetRequest request) {
        Asset asset = assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + id));

        AssetType assetType = assetTypeRepository.findById(request.getAssetTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset type not found: " + request.getAssetTypeId()));

        asset.setAssetType(assetType);
        asset.setBrand(request.getBrand());
        asset.setSerialNumber(request.getSerialNumber());
        asset.setPurchaseDate(request.getPurchaseDate());
        asset.setPurchaseCost(request.getPurchaseCost());
        asset.setSpecs(request.getSpecs());
        asset.setNotes(request.getNotes());

        return mapToResponse(assetRepository.save(asset));
    }

    @Transactional
    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }

    public AssetResponse mapToResponse(Asset asset) {
        AssetLocationHistory currentLocation = asset.getCurrentLocationRecord();
        AssetAssignmentHistory currentAssignment = asset.getCurrentAssignmentRecord();

        return AssetResponse.builder()
            .id(asset.getId())
            .code(asset.getCode())
            .assetTypeId(asset.getAssetType() != null ? asset.getAssetType().getId() : null)
            .assetTypeName(asset.getAssetType() != null ? asset.getAssetType().getName() : null)
            .grvEntryId(asset.getGrvEntry() != null ? asset.getGrvEntry().getId() : null)
            .brand(asset.getBrand())
            .serialNumber(asset.getSerialNumber())
            .status(asset.getCurrentStatus().name())
            .purchaseDate(asset.getPurchaseDate())
            .purchaseCost(asset.getPurchaseCost())
            .specs(asset.getSpecs())
            .notes(asset.getNotes())
            .currentLocation(currentLocation != null ? AssetLocationResponse.builder()
                .id(currentLocation.getId())
                .assetId(currentLocation.getAsset().getId())
                .officeId(currentLocation.getOffice().getId())
                .officeName(currentLocation.getOffice().getName())
                .campusName(currentLocation.getOffice().getCampus().getName())
                .validFrom(currentLocation.getValidFrom())
                .validTo(currentLocation.getValidTo())
                .build() : null)
            .currentAssignment(currentAssignment != null ? AssetAssignmentResponse.builder()
                .id(currentAssignment.getId())
                .assetId(currentAssignment.getAsset().getId())
                .userId(currentAssignment.getUser().getId())
                .username(currentAssignment.getUser().getUsername())
                .roleAtAssignment(currentAssignment.getRoleAtAssignment())
                .notes(currentAssignment.getNotes())
                .validFrom(currentAssignment.getValidFrom())
                .validTo(currentAssignment.getValidTo())
                .build() : null)
            .allowedActions(asset.getCurrentStatus().getAllowedActions())
            .createdAt(asset.getCreatedAt())
            .updatedAt(asset.getUpdatedAt())
            .build();
    }
}