package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory_items", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"asset_type_id", "brand", "location_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_type_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;

    @Column(length = 50)
    private String brand;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_asset_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset linkedAsset;

    @Column(columnDefinition = "JSON")
    private String specs;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<InventoryTransaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<InventoryGrvLink> grvLinks = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}