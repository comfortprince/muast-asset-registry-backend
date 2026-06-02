// model/ConsumableGrvLink.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumable_grv_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumableGrvLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumable_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Consumable consumable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grv_entry_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GrvEntry grvEntry;

    @Column(name = "quantity_contributed", nullable = false)
    private Integer quantityContributed;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}