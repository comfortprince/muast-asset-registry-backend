// repository/InventoryItemRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByLocationId(Long officeId);
    List<InventoryItem> findByAssetTypeId(Long assetTypeId);
    List<InventoryItem> findByLinkedAssetId(Long assetId);
    Optional<InventoryItem> findByAssetTypeIdAndBrandAndLocationId(
        Long assetTypeId, String brand, Long locationId);

    @Query("SELECT i FROM InventoryItem i WHERE i.quantity <= :threshold")
    List<InventoryItem> findLowStock(int threshold);
}