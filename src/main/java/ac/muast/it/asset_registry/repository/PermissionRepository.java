// repository/PermissionRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "permissions")
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    // EXPORT: GET /permissions (list all)
    @Override
    @RestResource(exported = true)
    Page<Permission> findAll(Pageable pageable);
    
    // EXPORT: GET /permissions/search/findByName?name=XXX
    @RestResource(exported = true)
    Optional<Permission> findByName(String name);
    
    // HIDE: GET /permissions/{id}
    @Override
    @RestResource(exported = false)
    Optional<Permission> findById(Long id);
    
    // HIDE: POST /permissions (create)
    @Override
    @RestResource(exported = false)
    <S extends Permission> S save(S entity);
    
    // HIDE: PUT /permissions/{id} (full update)
    @Override
    @RestResource(exported = false)
    <S extends Permission> S saveAndFlush(S entity);
    
    // HIDE: DELETE /permissions/{id}
    @Override
    @RestResource(exported = false)
    void deleteById(Long id);
    
    @Override
    @RestResource(exported = false)
    void delete(Permission entity);
    
    @Override
    @RestResource(exported = false)
    void deleteAll();
    
    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Permission> entities);
    
    // HIDE: PATCH /permissions/{id} (partial update)
    // (covered by save/saveAndFlush)
    
    // HIDE: Other inherited methods
    @Override
    @RestResource(exported = false)
    boolean existsById(Long id);
    
    @Override
    @RestResource(exported = false)
    long count();
}