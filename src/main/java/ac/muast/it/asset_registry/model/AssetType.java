package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asset_types", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"category_id", "name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "track_individual")
    @Builder.Default
    private Boolean trackIndividual = true;

    @Column(name = "track_quantity")
    @Builder.Default
    private Boolean trackQuantity = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;
}