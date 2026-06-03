// dto/request/CreateCampusRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCampusRequest {
    
    @NotBlank(message = "Campus code is required")
    private String code;
    
    @NotBlank(message = "Campus name is required")
    private String name;

    private String description;
    
    private String address;
}