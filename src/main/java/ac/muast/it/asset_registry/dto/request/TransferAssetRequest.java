// dto/request/TransferAssetRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferAssetRequest {
    @NotNull(message = "Office ID is required")
    private Long officeId;
    
    private String notes;
}