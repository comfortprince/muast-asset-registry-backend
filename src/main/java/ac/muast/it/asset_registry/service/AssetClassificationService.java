// service/CatalogService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.request.CreateAssetTypeRequest;
import ac.muast.it.asset_registry.dto.request.CreateCategoryRequest;
import ac.muast.it.asset_registry.dto.response.AssetTypeResponse;
import ac.muast.it.asset_registry.dto.response.CategoryResponse;
import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.model.AssetType;
import ac.muast.it.asset_registry.model.Category;
import ac.muast.it.asset_registry.repository.AssetTypeRepository;
import ac.muast.it.asset_registry.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetClassificationService {

    private final CategoryRepository categoryRepository;
    private final AssetTypeRepository assetTypeRepository;

    // =============================================
    // CATEGORIES
    // =============================================

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = Category.builder()
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .build();
        return mapCategoryToResponse(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(this::mapCategoryToResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return mapCategoryToResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CreateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return mapCategoryToResponse(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (assetTypeRepository.existsByCategoryId(id)) {
            throw new IllegalArgumentException("Cannot delete category with associated asset types.");
        }

        categoryRepository.deleteById(id);
    }

    // =============================================
    // ASSET TYPES
    // =============================================

    @Transactional
    public AssetTypeResponse createAssetType(Long categoryId, CreateAssetTypeRequest request) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));

        AssetType assetType = AssetType.builder()
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .trackIndividual(request.getTrackIndividual())
            .trackQuantity(request.getTrackQuantity())
            .trackConsumableReplacement(request.getTrackConsumableReplacement())
            .category(category)
            .build();
        return mapAssetTypeToResponse(assetTypeRepository.save(assetType));
    }

    @Transactional(readOnly = true)
    public Page<AssetTypeResponse> getAssetTypesByCategory(Long categoryId, Pageable pageable) {
        return assetTypeRepository.findByCategoryId(categoryId, pageable)
            .map(this::mapAssetTypeToResponse);
    }

    @Transactional
    public AssetTypeResponse updateAssetType(Long categoryId, Long assetTypeId, CreateAssetTypeRequest request) {
        AssetType assetType = assetTypeRepository.findByCategoryIdAndId(categoryId, assetTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset type not found: " + assetTypeId + " in category " + categoryId));

        assetType.setName(request.getName());
        assetType.setDescription(request.getDescription());
        assetType.setTrackIndividual(request.getTrackIndividual());
        assetType.setTrackQuantity(request.getTrackQuantity());
        assetType.setTrackConsumableReplacement(request.getTrackConsumableReplacement());
        return mapAssetTypeToResponse(assetTypeRepository.save(assetType));
    }

    @Transactional
    public void deleteAssetTypeByCategoryIdAndAssetTypeId(Long categoryId, Long assetTypeId) {
        AssetType assetType = assetTypeRepository.findByCategoryIdAndId(categoryId, assetTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset type not found: " + assetTypeId + " in category " + categoryId));
        
        assetTypeRepository.delete(assetType);
    }

    @Transactional(readOnly = true)
    public AssetTypeResponse getAssetTypeByCategoryIdAndAssetTypeId(Long categoryId, Long assetTypeId) {
        AssetType assetType = assetTypeRepository.findByCategoryIdAndId(categoryId, assetTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Asset type not found: " + assetTypeId + " in category " + categoryId ));
        return mapAssetTypeToResponse(assetType);
    }

    @Transactional(readOnly = true)
    public List<AssetTypeResponse> getAllAssetTypes() {
        return assetTypeRepository.findAll().stream()
            .map(this::mapAssetTypeToResponse)
            .toList();
    }

    // =============================================
    // MAPPERS
    // =============================================

    private CategoryResponse mapCategoryToResponse(Category category) {
        return CategoryResponse.builder()
            .id(category.getId())
            .code(category.getCode())
            .name(category.getName())
            .description(category.getDescription())
            .assetTypes(category.getAssetTypes() != null 
                ? category.getAssetTypes().stream().map(this::mapAssetTypeToResponse).toList() 
                : List.of())
            .build();
    }

    private AssetTypeResponse mapAssetTypeToResponse(AssetType assetType) {
        return AssetTypeResponse.builder()
            .id(assetType.getId())
            .name(assetType.getName())
            .code(assetType.getCode())
            .description(assetType.getDescription())
            .trackIndividual(assetType.getTrackIndividual())
            .trackQuantity(assetType.getTrackQuantity())
            .categoryId(assetType.getCategory() != null ? assetType.getCategory().getId() : null)
            .categoryName(assetType.getCategory() != null ? assetType.getCategory().getName() : null)
            .build();
    }
}