// repository/ConsumableStockRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.ConsumableStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ConsumableStockRepository extends JpaRepository<ConsumableStock, Long> {

    Optional<ConsumableStock> findByConsumableIdAndOfficeId(Long consumableId, Long officeId);

    Page<ConsumableStock> findByOfficeId(Long officeId, Pageable pageable);

    @Query("""
        SELECT cs FROM ConsumableStock cs 
        JOIN FETCH cs.consumable 
        JOIN FETCH cs.office 
        WHERE cs.quantity <= :threshold
    """)
    Page<ConsumableStock> findLowStock(@Param("threshold") int threshold, Pageable pageable);
}