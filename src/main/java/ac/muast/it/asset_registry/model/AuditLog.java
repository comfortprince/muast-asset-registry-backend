// model/AuditLog.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @Column(nullable = false, length = 50)
    private String action;            // e.g., "CREATE", "UPDATE", "DELETE", "ASSIGN"

    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;        // e.g., "Asset", "User", "GrvEntry"

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "old_values", columnDefinition = "JSON")
    private String oldValues;

    @Column(name = "new_values", columnDefinition = "JSON")
    private String newValues;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}