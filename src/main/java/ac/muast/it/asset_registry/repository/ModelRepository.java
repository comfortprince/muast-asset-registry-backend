package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByAssetTypeId(Long assetTypeId);
    Optional<Model> findByAssetTypeIdAndBrandAndModelNumber(
        Long assetTypeId, String brand, String modelNumber);
}