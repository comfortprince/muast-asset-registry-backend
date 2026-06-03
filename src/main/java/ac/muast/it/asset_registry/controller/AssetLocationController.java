// controller/AssetLocationController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.response.AssetLocationResponse;
import ac.muast.it.asset_registry.model.AssetHistory;
import ac.muast.it.asset_registry.repository.AssetLocationHistoryRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets/assetLocations")
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class AssetLocationController {

    private final AssetLocationHistoryRepository locationHistoryRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetLocationResponse> getAssetLocations(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> locations = locationHistoryRepository
            .findAll(pageable)
            .map(loc -> AssetLocationResponse.builder()
                .id(loc.getId())
                .assetId(loc.getAsset().getId())
                .officeId(loc.getOffice().getId())
                .officeName(loc.getOffice().getName())
                .campusName(loc.getOffice().getCampus().getName())
                .validFrom(loc.getValidFrom())
                .validTo(loc.getValidTo())
                .build()
            );
        return new PagedModel<>(locations);
    }

    @GetMapping("/search/byAsset/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetLocationResponse> getByAssetId(
        @PathVariable Long assetId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> locations = locationHistoryRepository
            .findByAssetIdOrderByValidFromDesc(assetId, pageable)
            .map(loc -> AssetLocationResponse.builder()
                .id(loc.getId())
                .assetId(loc.getAsset().getId())
                .officeId(loc.getOffice().getId())
                .officeName(loc.getOffice().getName())
                .campusName(loc.getOffice().getCampus().getName())
                .validFrom(loc.getValidFrom())
                .validTo(loc.getValidTo())
                .build()
            );
        return new PagedModel<>(locations);
    }

    @GetMapping("/search/currentAssets/{officeId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetLocationResponse> getCurrentAssetsInOffice(
        @PathVariable Long officeId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> locations = locationHistoryRepository
            .findCurrentByOfficeId(officeId, AssetHistory.MAX_VALID_TO, pageable)
            .map(loc -> AssetLocationResponse.builder()
                .id(loc.getId())
                .assetId(loc.getAsset().getId())
                .officeId(loc.getOffice().getId())
                .officeName(loc.getOffice().getName())
                .campusName(loc.getOffice().getCampus().getName())
                .validFrom(loc.getValidFrom())
                .validTo(loc.getValidTo())
                .build()
            );
        return new PagedModel<>(locations);
    }

    @GetMapping("/search/current/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<AssetLocationResponse> getCurrentLocation(@PathVariable Long assetId) {
        return locationHistoryRepository.findCurrentByAssetId(assetId, AssetHistory.MAX_VALID_TO)
            .map(loc -> AssetLocationResponse.builder()
                .id(loc.getId())
                .assetId(loc.getAsset().getId())
                .officeId(loc.getOffice().getId())
                .officeName(loc.getOffice().getName())
                .campusName(loc.getOffice().getCampus().getName())
                .validFrom(loc.getValidFrom())
                .validTo(loc.getValidTo())
                .build())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}