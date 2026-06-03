// dto/request/CreateOfficeRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOfficeRequest {
    
    @NotBlank(message = "Office code is required")
    private String code;
    
    @NotBlank(message = "Office name is required")
    private String name;
    
    private String description;
    private Boolean isActive;
}