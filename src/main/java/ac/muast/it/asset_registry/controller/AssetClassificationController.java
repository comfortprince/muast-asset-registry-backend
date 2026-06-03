// controller/AssetClassificationController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.CreateAssetTypeRequest;
import ac.muast.it.asset_registry.dto.request.CreateCategoryRequest;
import ac.muast.it.asset_registry.dto.response.AssetTypeResponse;
import ac.muast.it.asset_registry.dto.response.CategoryResponse;
import ac.muast.it.asset_registry.service.AssetClassificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets/categories")
@RequiredArgsConstructor
@Validated
public class AssetClassificationController {

    private final AssetClassificationService classificationService;

    // =============================================
    // CATEGORIES
    // =============================================

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(classificationService.createCategory(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(classificationService.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(classificationService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<CategoryResponse> updateCategory(
        @PathVariable Long id,
        @Valid @RequestBody CreateCategoryRequest request
    ) {
        return ResponseEntity.ok(classificationService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        classificationService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // =============================================
    // ASSET TYPES
    // =============================================

    @PostMapping("/{categoryId}/assetTypes")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<AssetTypeResponse> createAssetType(
        @PathVariable Long categoryId,
        @Valid @RequestBody CreateAssetTypeRequest request
    ) {
        return ResponseEntity.ok(classificationService.createAssetType(categoryId, request));
    }

    @GetMapping("/{categoryId}/assetTypes")
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<Page<AssetTypeResponse>> getAssetTypes(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(classificationService.getAssetTypesByCategory(categoryId, pageable));
    }
}