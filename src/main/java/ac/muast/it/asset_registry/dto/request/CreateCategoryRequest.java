// dto/request/CreateCategoryRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    
    @NotBlank(message = "Category code is required")
    private String code;
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    private String description;
}