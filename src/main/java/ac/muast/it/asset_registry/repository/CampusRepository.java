package ac.muast.it.asset_registry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_registry.model.Campus;

import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByName(String name);
    Optional<Campus> findByCode(String code);
    boolean existsByName(String name);
    Page<Campus> findAll(Pageable pageable);
}