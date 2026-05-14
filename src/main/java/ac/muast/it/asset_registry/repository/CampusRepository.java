package ac.muast.it.asset_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ac.muast.it.asset_registry.model.Campus;

import java.util.Optional;

@RepositoryRestResource
public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByName(String name);
}