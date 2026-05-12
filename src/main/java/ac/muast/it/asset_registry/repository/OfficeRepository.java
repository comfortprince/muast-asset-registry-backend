package ac.muast.it.asset_registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_registry.model.Office;

import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    Optional<Office> findByName(String name);
}