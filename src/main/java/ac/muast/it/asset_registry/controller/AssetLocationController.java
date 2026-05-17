// controller/AssetLocationController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.response.AssetLocationResponse;
import ac.muast.it.asset_registry.repository.AssetLocationRepository;
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
@RequestMapping("/api/assetLocations")
@RequiredArgsConstructor
@Validated
public class AssetLocationController {

    private final AssetLocationRepository assetLocationRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
	@Transactional(readOnly = true)
    public PagedModel<AssetLocationResponse> getAssetLocations(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> assetLocations = assetLocationRepository
			.findAll(pageable)
			.map(loc -> AssetLocationResponse.builder()
				.id(loc.getId())
				.assetId(loc.getAsset().getId())
				.officeId(loc.getOffice().getId())
				.isCurrent(loc.getIsCurrent())
				.assignedAt(loc.getAssignedAt())
				.build()
			);
        return new PagedModel<>(assetLocations);
    }

	@GetMapping("/search/byAsset/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
	@Transactional(readOnly = true)
    public PagedModel<AssetLocationResponse> getByAssetId(
        @PathVariable Long assetId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> assetLocations = assetLocationRepository
			.findByAssetIdOrderByAssignedAtDesc(assetId, pageable)
			.map(loc -> AssetLocationResponse.builder()
				.id(loc.getId())
				.assetId(loc.getAsset().getId())
				.officeId(loc.getOffice().getId())
				.isCurrent(loc.getIsCurrent())
				.assignedAt(loc.getAssignedAt())
				.build()
			);
        return new PagedModel<>(assetLocations);
    }

	@GetMapping("/search/currentAssets/{officeId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
	@Transactional(readOnly = true)
    public PagedModel<AssetLocationResponse> getCurrentAssetsInOffice(
        @PathVariable Long officeId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetLocationResponse> assetLocations = assetLocationRepository
			.findByOfficeIdAndIsCurrentTrue(officeId, pageable)
			.map(loc -> AssetLocationResponse.builder()
				.id(loc.getId())
				.assetId(loc.getAsset().getId())
				.officeId(loc.getOffice().getId())
				.isCurrent(loc.getIsCurrent())
				.assignedAt(loc.getAssignedAt())
				.build()
			);
        return new PagedModel<>(assetLocations);
    }

    @GetMapping("/search/current/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
	@Transactional(readOnly = true)
    public ResponseEntity<AssetLocationResponse> getCurrentLocation(@PathVariable Long assetId) {
        return assetLocationRepository.findByAssetIdAndIsCurrentTrue(assetId)
            .map(loc -> AssetLocationResponse.builder()
                .id(loc.getId())
                .assetId(loc.getAsset().getId())
                .officeId(loc.getOffice().getId())
                .isCurrent(loc.getIsCurrent())
                .assignedAt(loc.getAssignedAt())
                .build())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}