// repository/AssetAssignmentRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long> {
    List<AssetAssignment> findByAssetIdOrderByAssignedAtDesc(Long assetId);
    List<AssetAssignment> findByUserIdAndIsCurrentTrue(Long userId);
    Optional<AssetAssignment> findByAssetIdAndIsCurrentTrue(Long assetId);
}