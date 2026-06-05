// dto/request/AssignAssetRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckinAssetRequest {
    private String notes;

    @NotNull(message = "Return office is required when checking in an asset")
    private Long returnOfficeId;
}