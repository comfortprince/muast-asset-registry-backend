package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_assignments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"asset_id", "is_current"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;                // Nullable — assigned to a location instead

    @Column(name = "role_at_assignment", length = 100)
    private String roleAtAssignment;  // e.g., "Lecturer", "Lab Technician"

    @Column(name = "is_current")
    @Builder.Default
    private Boolean isCurrent = true;

    @Column(name = "assigned_at")
    @Builder.Default
    private LocalDateTime assignedAt = LocalDateTime.now();

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
