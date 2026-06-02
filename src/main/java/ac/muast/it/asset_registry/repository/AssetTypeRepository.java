// repository/AssetTypeRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    List<AssetType> findByCategoryId(Long categoryId);
    Optional<AssetType> findByCategoryIdAndId(Long categoryId, Long assetTypeId);
    List<AssetType> findByTrackIndividualTrue();
    List<AssetType> findByTrackQuantityTrue();
    boolean existsByCategoryId(Long categoryId);
}