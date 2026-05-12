// model/GrvItem.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "grv_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrvItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grv_entry_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GrvEntry grvEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Model model;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}