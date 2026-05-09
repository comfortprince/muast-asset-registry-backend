// repository/PermissionRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}