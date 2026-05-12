// model/Model.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "models", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"asset_type_id", "brand", "model_number"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String brand;             // e.g., "Dell", "HP"

    @Column(name = "model_number", nullable = false, length = 100)
    private String modelNumber;       // e.g., "Optiplex 7060", "LaserJet M404dn"

    @Column(columnDefinition = "JSON")
    private String specs;             // JSON string — can be mapped to a Map later

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;
}