// repository/ServiceEntryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {
    List<ServiceEntry> findByAssetIdOrderByDatePerformedDesc(Long assetId);
    List<ServiceEntry> findByServiceType(String serviceType);
    List<ServiceEntry> findByVendor(String vendor);
    List<ServiceEntry> findByAsset_AssetType_Id(Long assetTypeId);
}