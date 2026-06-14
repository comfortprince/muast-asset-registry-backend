// repository/AuditLogRepository.java
package ac.muast.it.asset_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_register.model.AuditAction;
import ac.muast.it.asset_register.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityNameAndEntityIdOrderByOccurredAtDesc(String entityName, Long entityId);
    List<AuditLog> findByUserIdOrderByOccurredAtDesc(Long userId);
    List<AuditLog> findByActionAndOccurredAtBetween(AuditAction action, LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);
}