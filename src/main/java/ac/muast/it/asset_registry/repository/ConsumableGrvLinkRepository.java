// repository/ConsumableGrvLinkRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.ConsumableGrvLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsumableGrvLinkRepository extends JpaRepository<ConsumableGrvLink, Long> {
    List<ConsumableGrvLink> findByConsumableId(Long consumableId);
    List<ConsumableGrvLink> findByGrvEntryId(Long grvEntryId);
}