// service/AuditService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.model.AuditAction;
import ac.muast.it.asset_registry.model.AuditLog;
import ac.muast.it.asset_registry.model.Auditable;
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.repository.AuditLogRepository;
import ac.muast.it.asset_registry.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Logs an audit action for a given entity. This method runs in a new transaction to ensure that audit logs are saved even if the main transaction rolls back.
     * @param action
     * @param entity
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(AuditAction action, Object entity) {
        log(action, entity, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(AuditAction action, Object entity, String oldValues) {
        try {
            AuditLog auditLog = AuditLog.builder()
                .user(getCurrentUser())
                .entityName(entity.getClass().getSimpleName())
                .entityId(getEntityId(entity))
                .action(action)
                .oldValues(oldValues)
                .newValues(toJson(entity))
                .occurredAt(LocalDateTime.now())
                .build();
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Failed to write audit log", e);
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    private Long getEntityId(Object entity) {
        if (entity instanceof Auditable auditable) {
            return auditable.getId();
        }
        return null;
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }
}