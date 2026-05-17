// repository/GrvEntryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.GrvEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GrvEntryRepository extends JpaRepository<GrvEntry, Long> {
    List<GrvEntry> findByReceivedDateBetween(LocalDate start, LocalDate end);
    List<GrvEntry> findByStorekeeperId(Long storekeeperId);
}