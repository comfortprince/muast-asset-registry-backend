// repository/StatusRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(String name);
}