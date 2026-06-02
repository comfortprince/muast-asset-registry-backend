// repository/AssetStatusHistoryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetStatusHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AssetStatusHistoryRepository extends JpaRepository<AssetStatusHistory, Long> {

    @Query("""
        SELECT ash FROM AssetStatusHistory ash 
        JOIN FETCH ash.asset 
        WHERE ash.asset.id = :assetId 
        ORDER BY ash.validFrom DESC
    """)
    Page<AssetStatusHistory> findByAssetIdOrderByValidFromDesc(@Param("assetId") Long assetId, Pageable pageable);

    @Query("""
        SELECT ash FROM AssetStatusHistory ash 
        JOIN FETCH ash.asset 
        WHERE ash.asset.id = :assetId 
        AND ash.validTo = '9000-01-01T00:00:00'
    """)
    Optional<AssetStatusHistory> findCurrentByAssetId(@Param("assetId") Long assetId);
}