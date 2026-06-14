// controller/AssetCategoryController.java
package ac.muast.it.asset_register.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ac.muast.it.asset_register.dto.request.AssetCategoryRequest;
import ac.muast.it.asset_register.dto.response.AssetCategoryResponse;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.AssetCategory;
import ac.muast.it.asset_register.repository.AssetCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/assets/categories")
@RequiredArgsConstructor
@Validated
public class AssetCategoryController {

    private final AssetCategoryRepository categoryRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<AssetCategoryResponse> createCategory(@Valid @RequestBody AssetCategoryRequest request) {
        AssetCategory category = AssetCategory.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
        return ResponseEntity.ok(mapToResponse(categoryRepository.save(category)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<List<AssetCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(
            categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ASSET_CATALOG')")
    public ResponseEntity<AssetCategoryResponse> getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(this::mapToResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<AssetCategoryResponse> updateCategory(
        @PathVariable Long id,
        @Valid @RequestBody AssetCategoryRequest request
    ) {
        AssetCategory category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return ResponseEntity.ok(mapToResponse(categoryRepository.save(category)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ASSET_CATALOG')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AssetCategoryResponse mapToResponse(AssetCategory category) {
        return AssetCategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .build();
    }
}