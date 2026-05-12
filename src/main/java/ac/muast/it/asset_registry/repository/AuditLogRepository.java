// repository/AuditLogRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, Long entityId);
    List<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AuditLog> findByActionAndCreatedAtBetween(String action, LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}