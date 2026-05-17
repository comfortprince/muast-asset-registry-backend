// repository/AssetLocationRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetLocation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssetLocationRepository extends JpaRepository<AssetLocation, Long> {
    @Query("""
        SELECT al FROM AssetLocation al 
        JOIN FETCH al.asset 
        JOIN FETCH al.office o 
        JOIN FETCH o.campus 
        WHERE al.asset.id = :assetId 
        ORDER BY al.assignedAt DESC
    """)
    Page<AssetLocation> findByAssetIdOrderByAssignedAtDesc(Long assetId, Pageable pageable);
    
    @Query("""
        SELECT al FROM AssetLocation al 
        JOIN FETCH al.asset 
        JOIN FETCH al.office o 
        JOIN FETCH o.campus 
        WHERE o.id = :officeId 
        AND al.isCurrent = true 
    """)
    Page<AssetLocation> findByOfficeIdAndIsCurrentTrue(Long officeId, Pageable pageable);
    Optional<AssetLocation> findByAssetIdAndIsCurrentTrue(Long assetId);
}