package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetStatusResponse {
    private Long id;
    private String name;
    private String description;
}