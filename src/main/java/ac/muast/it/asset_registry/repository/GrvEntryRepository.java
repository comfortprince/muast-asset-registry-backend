// repository/GrvEntryRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.GrvEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface GrvEntryRepository extends JpaRepository<GrvEntry, Long> {
    List<GrvEntry> findByReceivedDateBetween(LocalDate start, LocalDate end);
    List<GrvEntry> findByStorekeeperId(Long storekeeperId);
    
    @Query("SELECT g FROM GrvEntry g JOIN FETCH g.items WHERE g.id = :id")
    Optional<GrvEntry> findByIdWithItems(Long id);
}