package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_code", unique = true, nullable = false, length = 50)
    private String assetCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grv_item_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GrvItem grvItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;

    @Column(length = 50)
    private String brand;

    @Column(name = "serial_number", unique = true, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private AssetStatus status = AssetStatus.AVAILABLE;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_cost", precision = 10, scale = 2)
    private BigDecimal purchaseCost;

    @Column(name = "replacement_threshold", precision = 10, scale = 2)
    private BigDecimal replacementThreshold;

    @Column(name = "total_consumable_cost", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal totalConsumableCost = BigDecimal.ZERO;

    @Column(columnDefinition = "JSON")
    private String specs;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AssetLocation> locations = new ArrayList<>();

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AssetAssignment> assignments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public AssetLocation getCurrentLocation() {
        return locations.stream()
            .filter(AssetLocation::getIsCurrent)
            .findFirst()
            .orElse(null);
    }

    public AssetAssignment getCurrentAssignment() {
        return assignments.stream()
            .filter(AssetAssignment::getIsCurrent)
            .findFirst()
            .orElse(null);
    }
}