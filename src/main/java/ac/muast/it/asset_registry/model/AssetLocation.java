// model/AssetLocation.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_locations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"asset_id", "is_current"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Campus campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office office;

    @Column(name = "is_current")
    @Builder.Default
    private Boolean isCurrent = true;

    @Column(name = "assigned_at")
    @Builder.Default
    private LocalDateTime assignedAt = LocalDateTime.now();
}