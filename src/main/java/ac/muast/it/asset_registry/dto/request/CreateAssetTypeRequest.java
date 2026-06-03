// dto/request/CreateAssetTypeRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAssetTypeRequest {
    
    @NotBlank(message = "Asset type name is required")
    private String name;

    @NotBlank(message = "Asset type code is required")
    private String code;
    
    private String description;
    
    @NotNull(message = "Track individual is required")
    private Boolean trackIndividual;
    
    @NotNull(message = "Track quantity is required")
    private Boolean trackQuantity;

    @NotNull(message = "Track consumable replacement is required")
    private Boolean trackConsumableReplacement;
}