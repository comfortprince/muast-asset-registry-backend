// model/AssetTypeStatusId.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetTypeStatusId implements Serializable {

    private Long assetTypeId;
    private Long statusId;
}