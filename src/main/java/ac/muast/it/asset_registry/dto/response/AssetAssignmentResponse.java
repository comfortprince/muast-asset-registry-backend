package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetAssignmentResponse {
    private Long id;
    private Long assetId;
    private String assetCode;
    private String assetBrand;
    private Long userId;
    private String username;
    private String roleAtAssignment;
    private Boolean isCurrent;
    private LocalDateTime assignedAt;
    private LocalDateTime returnedAt;
    private String notes;
}