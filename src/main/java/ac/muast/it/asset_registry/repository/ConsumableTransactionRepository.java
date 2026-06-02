// repository/ConsumableTransactionRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.ConsumableTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsumableTransactionRepository extends JpaRepository<ConsumableTransaction, Long> {

    @Query("""
        SELECT ct FROM ConsumableTransaction ct 
        JOIN FETCH ct.consumable 
        LEFT JOIN FETCH ct.sourceOffice 
        LEFT JOIN FETCH ct.destinationOffice 
        LEFT JOIN FETCH ct.asset 
        WHERE ct.consumable.id = :consumableId 
        ORDER BY ct.transactionDate DESC
    """)
    Page<ConsumableTransaction> findByConsumableIdOrderByTransactionDateDesc(
        @Param("consumableId") Long consumableId, Pageable pageable);

    @Query("""
        SELECT ct FROM ConsumableTransaction ct 
        JOIN FETCH ct.consumable 
        WHERE ct.asset.id = :assetId 
        ORDER BY ct.transactionDate DESC
    """)
    Page<ConsumableTransaction> findByAssetIdOrderByTransactionDateDesc(
        @Param("assetId") Long assetId, Pageable pageable);
}