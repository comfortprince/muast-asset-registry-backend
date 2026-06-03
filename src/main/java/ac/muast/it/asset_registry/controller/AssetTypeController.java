// controller/AssetTypeController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.CreateAssetTypeRequest;
import ac.muast.it.asset_registry.dto.response.AssetTypeResponse;
import ac.muast.it.asset_registry.service.AssetClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetTypeController {

    private final AssetClassificationService classificationService;

    @GetMapping("/asset-types")
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<List<AssetTypeResponse>> getAllAssetTypes() {
        return ResponseEntity.ok(classificationService.getAllAssetTypes());
    }

    @GetMapping("/asset-types/{id}")
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<AssetTypeResponse> getAssetType(@PathVariable Long id) {
        // Need a getById without categoryId in service
        return ResponseEntity.ok(classificationService.getAssetTypeById(id));
    }

    @PutMapping("/asset-types/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<AssetTypeResponse> updateAssetType(
        @PathVariable Long id,
        @Valid @RequestBody CreateAssetTypeRequest request
    ) {
        return ResponseEntity.ok(classificationService.updateAssetType(id, request));
    }

    @DeleteMapping("/asset-types/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<Void> deleteAssetType(@PathVariable Long id) {
        classificationService.deleteAssetType(id);
        return ResponseEntity.noContent().build();
    }
}