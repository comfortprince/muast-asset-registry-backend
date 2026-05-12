// repository/AssetTypeStatusRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AssetTypeStatus;
import ac.muast.it.asset_registry.model.AssetTypeStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetTypeStatusRepository extends JpaRepository<AssetTypeStatus, AssetTypeStatusId> {
    List<AssetTypeStatus> findByAssetTypeId(Long assetTypeId);
    List<AssetTypeStatus> findByAssetTypeIdOrderBySortOrderAsc(Long assetTypeId);
    List<AssetTypeStatus> findByAssetTypeIdAndIsDefaultTrue(Long assetTypeId);
}