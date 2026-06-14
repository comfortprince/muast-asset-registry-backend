// service/AssetService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.CreateAssetRequest;
import ac.muast.it.asset_register.dto.response.*;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.*;
import ac.muast.it.asset_register.repository.*;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetStatusHistoryRepository statusHistoryRepository;
    private final AssetStatusService assetStatusService;

    @Transactional
    public AssetResponse createAsset(CreateAssetRequest request) {
        AssetCategory category = assetCategoryRepository.findById(request.getAssetCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset category not found: " + request.getAssetCategoryId()));

        Asset asset = Asset.builder()
            .code(request.getCode())
            .assetCategory(category)
            .brand(request.getBrand())
            .serialNumber(request.getSerialNumber())
            .currentStatus(assetStatusService.getByName(AssetStatus.AVAILABLE))
            .purchaseDate(request.getPurchaseDate())
            .purchaseCost(request.getPurchaseCost())
            .specs(request.getSpecs())
            .notes(request.getNotes())
            .build();

        asset = assetRepository.save(asset);

        AssetStatusHistory statusHistory = AssetStatusHistory.builder()
            .asset(asset)
            .status(assetStatusService.getByName(AssetStatus.AVAILABLE))
            .reason("Asset created")
            .validFrom(java.time.LocalDateTime.now())
            .validTo(AssetHistory.MAX_VALID_TO)
            .build();
        statusHistoryRepository.save(statusHistory);

        return mapToResponse(asset);
    }

    @Transactional(readOnly = true)
    public Page<AssetResponse> getAllAssets(
        Pageable pageable, AssetStatus status,
        Long assetCategoryId, String brand,
        String serialNumber, String assetCode
    ) {
        Page<Asset> assets = assetRepository.findFiltered(status, assetCategoryId, brand, serialNumber, assetCode, pageable);
        return assets.map(this::mapToResponse);
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

        AssetCategory category = assetCategoryRepository.findById(request.getAssetCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset category not found: " + request.getAssetCategoryId()));

        asset.setAssetCategory(category);
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
            .assetCategoryId(asset.getAssetCategory() != null ? asset.getAssetCategory().getId() : null)
            .assetCategoryName(asset.getAssetCategory() != null ? asset.getAssetCategory().getName() : null)
            .brand(asset.getBrand())
            .serialNumber(asset.getSerialNumber())
            .status(asset.getCurrentStatus().getName())
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