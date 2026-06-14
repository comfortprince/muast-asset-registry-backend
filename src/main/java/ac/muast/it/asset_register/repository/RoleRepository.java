// repository/RoleRepository.java
package ac.muast.it.asset_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_register.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}