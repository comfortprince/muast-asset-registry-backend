// repository/AssetRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Asset;
import ac.muast.it.asset_registry.model.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByCode(String code);

    Optional<Asset> findBySerialNumber(String serialNumber);

    List<Asset> findByAssetTypeId(Long assetTypeId);

    List<Asset> findByCurrentStatus(AssetStatus status);

    List<Asset> findByBrand(String brand);

    boolean existsByCode(String code);

    boolean existsBySerialNumber(String serialNumber);

    @Query("SELECT a FROM Asset a JOIN FETCH a.assetType WHERE a.id = :id")
    Optional<Asset> findByIdWithDetails(@Param("id") Long id);
}