// repository/RoleRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}