// model/AssetTypeStatus.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asset_type_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetTypeStatus {

    @EmbeddedId
    private AssetTypeStatusId id;

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assetTypeId")
    @JoinColumn(name = "asset_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssetType assetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusId")
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Status status;
}