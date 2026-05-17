// dto/request/AssignAssetRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignAssetRequest {
    @NotBlank(message = "Username is required")
    private String username;

    private String roleAtAssignment;
    private String notes;
}