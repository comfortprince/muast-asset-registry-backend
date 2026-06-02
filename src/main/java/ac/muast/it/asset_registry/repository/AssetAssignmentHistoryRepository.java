// repository/AssetAssignmentHistoryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetAssignmentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AssetAssignmentHistoryRepository extends JpaRepository<AssetAssignmentHistory, Long> {

    @Query("""
        SELECT aah FROM AssetAssignmentHistory aah 
        JOIN FETCH aah.asset 
        JOIN FETCH aah.user 
        WHERE aah.asset.id = :assetId 
        ORDER BY aah.validFrom DESC
    """)
    Page<AssetAssignmentHistory> findByAssetIdOrderByValidFromDesc(@Param("assetId") Long assetId, Pageable pageable);

    @Query("""
        SELECT aah FROM AssetAssignmentHistory aah 
        JOIN FETCH aah.asset 
        JOIN FETCH aah.user 
        WHERE aah.asset.id = :assetId 
        AND aah.validTo = '9000-01-01T00:00:00'
    """)
    Optional<AssetAssignmentHistory> findCurrentByAssetId(@Param("assetId") Long assetId);

    @Query("""
        SELECT aah FROM AssetAssignmentHistory aah 
        JOIN FETCH aah.asset 
        JOIN FETCH aah.user 
        WHERE aah.user.id = :userId 
        ORDER BY aah.validFrom DESC
    """)
    Page<AssetAssignmentHistory> findByUserIdOrderByValidFromDesc(@Param("userId") Long userId, Pageable pageable);
}