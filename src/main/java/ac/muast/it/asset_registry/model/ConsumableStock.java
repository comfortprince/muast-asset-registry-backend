// model/ConsumableStock.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consumable_stock", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"consumable_id", "office_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumableStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumable_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Consumable consumable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office office;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;
}