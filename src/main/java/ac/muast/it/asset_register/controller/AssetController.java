// controller/AssetController.java
package ac.muast.it.asset_register.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ac.muast.it.asset_register.dto.request.AssignAssetRequest;
import ac.muast.it.asset_register.dto.request.CheckinAssetRequest;
import ac.muast.it.asset_register.dto.request.CreateAssetRequest;
import ac.muast.it.asset_register.dto.request.MarkForRepairRequest;
import ac.muast.it.asset_register.dto.request.RecoverAssetRequest;
import ac.muast.it.asset_register.dto.request.StatusUpdateRequest;
import ac.muast.it.asset_register.dto.request.TransferAssetRequest;
import ac.muast.it.asset_register.dto.response.AssetResponse;
import ac.muast.it.asset_register.dto.response.AssetStatusResponse;
import ac.muast.it.asset_register.model.AssetStatus;
import ac.muast.it.asset_register.service.AssetManagementService;
import ac.muast.it.asset_register.service.AssetService;
import ac.muast.it.asset_register.service.AssetStatusService;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetManagementService assetManagementService;
    private final AssetService assetService;
    private final AssetStatusService assetStatusService;

    // =============================================
    // ASSET CRUD
    // =============================================

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> createAsset(@Valid @RequestBody CreateAssetRequest request) {
        return ResponseEntity.ok(assetService.createAsset(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<Page<AssetResponse>> getAllAssets(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size,
        @RequestParam(required = false) AssetStatus status,
        @RequestParam(required = false) Long asset_category_id,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false, name = "serial_number") String serial_number,
        @RequestParam(required = false, name = "asset_code") String asset_code
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
            assetService.getAllAssets(pageable, status, asset_category_id, brand, serial_number, asset_code)
        );
    }

    @GetMapping("/{asset_id}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<AssetResponse> getAsset(@PathVariable Long asset_id) {
        return ResponseEntity.ok(assetService.getAssetById(asset_id));
    }

    @PutMapping("/{asset_id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> updateAsset(
        @PathVariable Long asset_id,
        @Valid @RequestBody CreateAssetRequest request
    ) {
        return ResponseEntity.ok(assetService.updateAsset(asset_id, request));
    }

    @DeleteMapping("/{asset_id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long asset_id) {
        assetService.deleteAsset(asset_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/asset-statuses")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<List<AssetStatusResponse>> getAssetStatuses() {
        List<AssetStatusResponse> assetStatuses = assetStatusService.getAll().stream()
            .map(status -> AssetStatusResponse.builder()
                .id(status.getId())
                .name(status.getName())
                .description(status.getDescription())
                .build()
            )
            .toList();

        return ResponseEntity.ok(assetStatuses);
    }

    // =============================================
    // ASSET OPERATIONS
    // =============================================

    @PostMapping("/{asset_id}/assign")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> assignAsset(
        @PathVariable Long asset_id,
        @Valid @RequestBody AssignAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.assignAsset(asset_id, request));
    }

    @PostMapping("/{asset_id}/transfer")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> transferAsset(
        @PathVariable Long asset_id,
        @Valid @RequestBody TransferAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.transferAsset(asset_id, request));
    }

    @PostMapping("/{asset_id}/checkin")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> checkinAsset(
        @PathVariable Long asset_id,
        @Valid @RequestBody CheckinAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.checkinAsset(asset_id, request));
    }

    @PostMapping("/{asset_id}/repair")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> markForRepair(
        @PathVariable Long asset_id,
        @Valid @RequestBody MarkForRepairRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.markForRepair(asset_id, request));
    }

    @PostMapping("/{asset_id}/lost")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> markAsLost(
        @PathVariable Long asset_id,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetManagementService.markAsLost(asset_id, notes));
    }

    @PostMapping("/{asset_id}/decommission")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> decommissionAsset(
        @PathVariable Long asset_id,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetManagementService.decommissionAsset(asset_id, notes));
    }

    @PostMapping("/{asset_id}/recover")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> recoverAsset(
        @PathVariable Long asset_id,
        @Valid @RequestBody RecoverAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.recoverAsset(asset_id, request));
    }
}