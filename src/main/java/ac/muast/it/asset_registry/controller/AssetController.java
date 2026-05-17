// controller/AssetController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.AssignAssetRequest;
import ac.muast.it.asset_registry.dto.request.StatusUpdateRequest;
// import ac.muast.it.asset_registry.dto.request.RepairAssetRequest;
// import ac.muast.it.asset_registry.dto.request.StatusUpdateRequest;
import ac.muast.it.asset_registry.dto.request.TransferAssetRequest;
// import ac.muast.it.asset_registry.dto.response.AssetResponse;
import ac.muast.it.asset_registry.model.Asset;
import ac.muast.it.asset_registry.model.AssetStatus;
import ac.muast.it.asset_registry.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping("/{assetCode}/assign")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> assignAsset(
        @PathVariable String assetCode,
        @Valid @RequestBody AssignAssetRequest request
    ) {
        return ResponseEntity.ok(assetService.assignAsset(assetCode, request));
    }

    @PostMapping("/{assetCode}/transfer")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> transferAsset(
        @PathVariable String assetCode,
        @Valid @RequestBody TransferAssetRequest request
    ) {
        return ResponseEntity.ok(assetService.transferAsset(assetCode, request));
    }

    @PostMapping("/{assetCode}/checkin")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> checkinAsset(@PathVariable String assetCode) {
        return ResponseEntity.ok(assetService.checkinAsset(assetCode));
    }

    @PostMapping("/{assetCode}/repair")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> sendForRepair(
        @PathVariable String assetCode
    ) {
        return ResponseEntity.ok(assetService.sendForRepair(assetCode));
    }

    @PostMapping("/{assetCode}/lost")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> markAsLost(
        @PathVariable String assetCode,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetService.markAsLost(assetCode, notes));
    }

    @PostMapping("/{assetCode}/decommission")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> decommissionAsset(
        @PathVariable String assetCode,
        @RequestBody(required = false) StatusUpdateRequest request
    ) {
        String notes = request != null ? request.getNotes() : null;
        return ResponseEntity.ok(assetService.decommissionAsset(assetCode, notes));
    }

    @PostMapping("/{assetCode}/recover")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<Asset> recoverAsset(
        @PathVariable String assetCode,
        @RequestParam AssetStatus targetStatus
    ) {
        return ResponseEntity.ok(assetService.recoverAsset(assetCode, targetStatus));
    }
}