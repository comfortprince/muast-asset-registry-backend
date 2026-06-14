// dto/request/AssetCategoryRequest.java
package ac.muast.it.asset_register.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssetCategoryRequest {
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    private String description;
}