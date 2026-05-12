// repository/AssetRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByAssetCode(String assetCode);
    Optional<Asset> findBySerialNumber(String serialNumber);
    List<Asset> findByAssetTypeId(Long assetTypeId);
    List<Asset> findByStatusId(Long statusId);
    List<Asset> findByModelId(Long modelId);
    boolean existsByAssetCode(String assetCode);
    boolean existsBySerialNumber(String serialNumber);

    @Query("SELECT a FROM Asset a JOIN FETCH a.status JOIN FETCH a.assetType WHERE a.id = :id")
    Optional<Asset> findByIdWithDetails(Long id);
}