// model/AssetLocationHistory.java
package ac.muast.it.asset_register.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset_location_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetLocationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office office;

    @Column(name = "notes", nullable = true)
    private String notes;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to", nullable = false)
    private LocalDateTime validTo;
}