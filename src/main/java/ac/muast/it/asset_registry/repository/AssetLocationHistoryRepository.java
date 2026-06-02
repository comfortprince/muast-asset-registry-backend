// repository/AssetLocationHistoryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetLocationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AssetLocationHistoryRepository extends JpaRepository<AssetLocationHistory, Long> {

    @Query("""
        SELECT alh FROM AssetLocationHistory alh 
        JOIN FETCH alh.asset 
        JOIN FETCH alh.office 
        WHERE alh.asset.id = :assetId 
        ORDER BY alh.validFrom DESC
    """)
    Page<AssetLocationHistory> findByAssetIdOrderByValidFromDesc(@Param("assetId") Long assetId, Pageable pageable);

    @Query("""
        SELECT alh FROM AssetLocationHistory alh 
        JOIN FETCH alh.asset 
        JOIN FETCH alh.office 
        WHERE alh.asset.id = :assetId 
        AND alh.validTo = '9000-01-01T00:00:00'
    """)
    Optional<AssetLocationHistory> findCurrentByAssetId(@Param("assetId") Long assetId);

    @Query("""
        SELECT alh FROM AssetLocationHistory alh 
        JOIN FETCH alh.asset 
        JOIN FETCH alh.office 
        WHERE alh.office.id = :officeId 
        AND alh.validTo = '9000-01-01T00:00:00'
    """)
    Page<AssetLocationHistory> findCurrentByOfficeId(@Param("officeId") Long officeId, Pageable pageable);
}