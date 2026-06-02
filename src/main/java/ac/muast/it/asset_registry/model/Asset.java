// model/Asset.java
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

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grv_entry_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GrvEntry grvEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;

    @Column(name = "current_status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AssetStatus currentStatus = AssetStatus.AVAILABLE;

    @Column(length = 50)
    private String brand;

    @Column(name = "serial_number", unique = true, length = 100)
    private String serialNumber;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_cost", precision = 10, scale = 2)
    private BigDecimal purchaseCost;

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
    private List<AssetStatusHistory> statusHistory = new ArrayList<>();

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AssetLocationHistory> locationHistory = new ArrayList<>();

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AssetAssignmentHistory> assignmentHistory = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public AssetStatusHistory getCurrentStatusRecord() {
        return statusHistory.stream()
            .filter(s -> s.getValidTo().equals(AssetHistory.MAX_VALID_TO))
            .findFirst()
            .orElse(null);
    }

    public AssetLocationHistory getCurrentLocationRecord() {
        return locationHistory.stream()
            .filter(l -> l.getValidTo().equals(AssetHistory.MAX_VALID_TO))
            .findFirst()
            .orElse(null);
    }

    public AssetAssignmentHistory getCurrentAssignmentRecord() {
        return assignmentHistory.stream()
            .filter(a -> a.getValidTo().equals(AssetHistory.MAX_VALID_TO))
            .findFirst()
            .orElse(null);
    }
}