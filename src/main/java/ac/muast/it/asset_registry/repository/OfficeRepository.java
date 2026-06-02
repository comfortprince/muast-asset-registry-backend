package ac.muast.it.asset_registry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.muast.it.asset_registry.model.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    Page<Office> findByCampusId(Long campusId, Pageable pageable);

    Optional<Office> findByCampusIdAndCode(Long campusId, String code);

    boolean existsByCampusId(Long campusId);

    @Query("""
        SELECT o FROM Office o 
        JOIN FETCH o.campus 
        WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Office> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("""
        SELECT o FROM Office o 
        JOIN FETCH o.campus 
        WHERE o.campus.id = :campusId 
        AND LOWER(o.name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Office> findByCampusIdAndNameContainingIgnoreCase(
        @Param("campusId") Long campusId,
        @Param("name") String name
    );
}