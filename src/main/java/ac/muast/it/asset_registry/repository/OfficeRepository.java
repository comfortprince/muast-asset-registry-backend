package ac.muast.it.asset_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ac.muast.it.asset_registry.model.Office;

import java.util.Optional;

@RepositoryRestResource
public interface OfficeRepository extends JpaRepository<Office, Long> {
    Optional<Office> findByName(String name);
}