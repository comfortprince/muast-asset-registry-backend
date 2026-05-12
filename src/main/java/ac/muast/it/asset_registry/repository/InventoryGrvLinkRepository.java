// repository/InventoryGrvLinkRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.InventoryGrvLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryGrvLinkRepository extends JpaRepository<InventoryGrvLink, Long> {
    List<InventoryGrvLink> findByInventoryItemId(Long inventoryItemId);
    List<InventoryGrvLink> findByGrvItemId(Long grvItemId);
}