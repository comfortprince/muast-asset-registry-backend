// repository/AssetLocationRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssetLocationRepository extends JpaRepository<AssetLocation, Long> {
    List<AssetLocation> findByAssetIdOrderByAssignedAtDesc(Long assetId);
    List<AssetLocation> findByOfficeIdAndIsCurrentTrue(Long officeId);
    Optional<AssetLocation> findByAssetIdAndIsCurrentTrue(Long assetId);
}