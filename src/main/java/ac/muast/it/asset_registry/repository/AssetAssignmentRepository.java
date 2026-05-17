// repository/AssetAssignmentRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long> {
    
    @Query("""
        SELECT aa FROM AssetAssignment aa 
        JOIN FETCH aa.asset 
        LEFT JOIN FETCH aa.user 
        WHERE aa.asset.id = :assetId 
        ORDER BY aa.assignedAt DESC
    """)
    Page<AssetAssignment> findByAssetIdOrderByAssignedAtDesc(@Param("assetId") Long assetId, Pageable pageable);
    
    @Query("""
        SELECT aa FROM AssetAssignment aa 
        JOIN FETCH aa.asset 
        LEFT JOIN FETCH aa.user 
        WHERE aa.user.id = :userId 
        ORDER BY aa.assignedAt DESC
    """)
    Page<AssetAssignment> findByUserIdOrderByAssignedAtDesc(@Param("userId") Long userId, Pageable pageable);
    
    @Query("""
        SELECT aa FROM AssetAssignment aa 
        JOIN FETCH aa.asset 
        LEFT JOIN FETCH aa.user 
        WHERE aa.asset.id = :assetId 
        AND aa.isCurrent = true
    """)
    Optional<AssetAssignment> findByAssetIdAndIsCurrentTrue(@Param("assetId") Long assetId);
    
    @Override
    @Query("""
        SELECT aa FROM AssetAssignment aa 
        JOIN FETCH aa.asset 
        LEFT JOIN FETCH aa.user
    """)
    Page<AssetAssignment> findAll(Pageable pageable);
}