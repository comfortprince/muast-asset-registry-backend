// dto/response/AssetTypeResponse.java
package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetTypeResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Boolean trackIndividual;
    private Boolean trackQuantity;
    private Boolean trackConsumableReplacement;
    private Long categoryId;
    private String categoryName;
}