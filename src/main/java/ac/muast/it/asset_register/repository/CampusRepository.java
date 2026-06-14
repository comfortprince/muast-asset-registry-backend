package ac.muast.it.asset_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_register.model.Campus;

import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByName(String name);
    Optional<Campus> findByCode(String code);
    boolean existsByName(String name);
}