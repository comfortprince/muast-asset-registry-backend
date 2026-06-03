// controller/AssetController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.AssignAssetRequest;
import ac.muast.it.asset_registry.dto.request.CreateAssetRequest;
import ac.muast.it.asset_registry.dto.request.StatusUpdateRequest;
import ac.muast.it.asset_registry.dto.request.TransferAssetRequest;
import ac.muast.it.asset_registry.dto.response.AssetResponse;
import ac.muast.it.asset_registry.model.AssetStatus;
import ac.muast.it.asset_registry.service.AssetManagementService;
import ac.muast.it.asset_registry.service.AssetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetManagementService assetManagementService;
    private final AssetService assetService;

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
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(assetService.getAllAssets(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<AssetResponse> getAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> updateAsset(
        @PathVariable Long id,
        @Valid @RequestBody CreateAssetRequest request
    ) {
        return ResponseEntity.ok(assetService.updateAsset(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/asset-statuses")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<List<Map<String, String>>> getAssetStatuses() {
        return ResponseEntity.ok(
            Arrays.stream(AssetStatus.values())
                .map(status -> Map.of(
                    "name", status.name(),
                    "displayName", status.getDisplayName(),
                    "description", status.getDescription()
                ))
                .toList()
        );
    }

    // =============================================
    // ASSET OPERATIONS
    // =============================================

    @PostMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> assignAsset(
        @PathVariable Long id,
        @Valid @RequestBody AssignAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.assignAsset(id, request));
    }

    @PostMapping("/{id}/transfer")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> transferAsset(
        @PathVariable Long id,
        @Valid @RequestBody TransferAssetRequest request
    ) {
        return ResponseEntity.ok(assetManagementService.transferAsset(id, request));
    }

    @PostMapping("/{id}/checkin")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> checkinAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetManagementService.checkinAsset(id));
    }

    @PostMapping("/{id}/repair")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> sendForRepair(@PathVariable Long id) {
        return ResponseEntity.ok(assetManagementService.sendForRepair(id));
    }

    @PostMapping("/{id}/lost")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> markAsLost(
        @PathVariable Long id,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetManagementService.markAsLost(id, notes));
    }

    @PostMapping("/{id}/decommission")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> decommissionAsset(
        @PathVariable Long id,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetManagementService.decommissionAsset(id, notes));
    }

    @PostMapping("/{id}/recover")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<AssetResponse> recoverAsset(
        @PathVariable Long id,
        @RequestParam AssetStatus targetStatus
    ) {
        return ResponseEntity.ok(assetManagementService.recoverAsset(id, targetStatus));
    }
}