// dto/response/AssetAssignmentResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetAssignmentResponse {
    private Long id;
    private Long assetId;
    private Long userId;
    private String username;
    private String roleAtAssignment;
    private String notes;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}