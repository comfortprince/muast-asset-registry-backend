// repository/ConsumableRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConsumableRepository extends JpaRepository<Consumable, Long> {
    Optional<Consumable> findByAssetTypeIdAndBrand(Long assetTypeId, String brand);
}