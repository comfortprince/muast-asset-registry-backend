// repository/AssetRepository.java
package ac.muast.it.asset_register.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.muast.it.asset_register.model.Asset;
import ac.muast.it.asset_register.model.AssetStatus;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByCode(String code);

    Optional<Asset> findBySerialNumber(String serialNumber);

    List<Asset> findByAssetCategoryId(Long assetCategoryId);

    List<Asset> findByCurrentStatus(AssetStatus status);

    List<Asset> findByBrand(String brand);

    boolean existsByCode(String code);

    boolean existsBySerialNumber(String serialNumber);

    @Query("SELECT a FROM Asset a JOIN FETCH a.assetCategory WHERE a.id = :id")
    Optional<Asset> findByIdWithDetails(@Param("id") Long id);

    @Query("""
        SELECT a FROM Asset a
        JOIN FETCH a.assetCategory
        WHERE (:status IS NULL OR a.currentStatus = :status)
        AND (:assetCategoryId IS NULL OR a.assetCategory.id = :assetCategoryId)
        AND (:brand IS NULL OR LOWER(a.brand) = LOWER(:brand))
        AND (:serialNumber IS NULL OR LOWER(a.serialNumber) = LOWER(:serialNumber))
        AND (:assetCode IS NULL OR LOWER(a.code) = LOWER(:assetCode))
    """)
    Page<Asset> findFiltered(
        @Param("status") AssetStatus status,
        @Param("assetCategoryId") Long assetCategoryId,
        @Param("brand") String brand,
        @Param("serialNumber") String serialNumber,
        @Param("assetCode") String assetCode,
        Pageable pageable
    );
}