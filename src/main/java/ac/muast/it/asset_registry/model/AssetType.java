package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String name;              // e.g., "DESKTOP", "LAPTOP"

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;        // e.g., "Desktop Computer"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "track_individual")
    @Builder.Default
    private Boolean trackIndividual = true;

    @Column(name = "track_quantity")
    @Builder.Default
    private Boolean trackQuantity = false;

    @Column(name = "allow_temporary_loans")
    @Builder.Default
    private Boolean allowTemporaryLoans = false;

    @Column(name = "has_service_records")
    @Builder.Default
    private Boolean hasServiceRecords = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @OneToMany(mappedBy = "assetType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Model> models = new ArrayList<>();

    // In AssetType.java — add this field
    @OneToMany(mappedBy = "assetType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AssetTypeStatus> allowedStatuses = new ArrayList<>();
}