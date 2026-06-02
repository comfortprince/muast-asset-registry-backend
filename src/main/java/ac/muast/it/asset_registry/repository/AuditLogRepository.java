// repository/AuditLogRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.AuditLog;
import ac.muast.it.asset_registry.model.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityNameAndEntityIdOrderByOccurredAtDesc(String entityName, Long entityId);
    List<AuditLog> findByUserIdOrderByOccurredAtDesc(Long userId);
    List<AuditLog> findByActionAndOccurredAtBetween(AuditAction action, LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);
}