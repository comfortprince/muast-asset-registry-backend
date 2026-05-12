package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.GrvItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrvItemRepository extends JpaRepository<GrvItem, Long> {
    List<GrvItem> findByGrvEntryId(Long grvEntryId);
    List<GrvItem> findByAssetTypeId(Long assetTypeId);
    List<GrvItem> findBySerialNumber(String serialNumber);
}