// repository/InventoryItemRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByCurrentLocationId(Long officeId);
    List<InventoryItem> findByAssetTypeId(Long assetTypeId);
    Optional<InventoryItem> findByAssetTypeIdAndModelIdAndCurrentLocationId(
        Long assetTypeId, Long modelId, Long locationId);

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= 5")
    List<InventoryItem> findLowStock(int threshold);
}