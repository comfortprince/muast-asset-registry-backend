// repository/UserRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
@PreAuthorize("hasAuthority('READ_USERS')")
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Override
    @PreAuthorize("hasAuthority('READ_USERS')")
    List<User> findAll();
    
    @Override
    @PreAuthorize("hasAuthority('READ_USERS')")
    Optional<User> findById(Long id);
    
    @Override
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    User save(User user);
    
    @Override
    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    void delete(User user);
    
    @PreAuthorize("hasAuthority('READ_USERS')")
    Optional<User> findByEmail(String email);
    
    @PreAuthorize("hasAuthority('READ_USERS')")
    Optional<User> findByUsername(String username);
    
    @PreAuthorize("hasAuthority('READ_USERS')")
    boolean existsByEmail(String email);
    
    @PreAuthorize("hasAuthority('READ_USERS')")
    boolean existsByUsername(String username);
}