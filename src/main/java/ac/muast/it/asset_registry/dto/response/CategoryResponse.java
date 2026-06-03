// dto/response/CategoryResponse.java
package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String code;
    private String description;
    private List<AssetTypeResponse> assetTypes;
}