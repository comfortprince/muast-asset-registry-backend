// dto/request/AssignAssetRequest.java
package ac.muast.it.asset_register.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignAssetRequest {
    @NotNull(message = "User id is required")
    private Long userId;

    private String roleAtAssignment;
    private String notes;
}