// repository/InventoryTransactionRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    List<InventoryTransaction> findByInventoryItemIdOrderByCreatedAtDesc(Long inventoryItemId);
    List<InventoryTransaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
    List<InventoryTransaction> findByToLocationIdAndTransactionDateAfter(Long officeId, LocalDate date);
}