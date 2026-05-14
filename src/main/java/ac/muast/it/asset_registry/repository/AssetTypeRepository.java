package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
    List<AssetType> findByCategoryId(Long categoryId);
    Optional<AssetType> findByCategoryIdAndName(Long categoryId, String name);
    List<AssetType> findByTrackIndividualTrue();
    List<AssetType> findByTrackQuantityTrue();
}