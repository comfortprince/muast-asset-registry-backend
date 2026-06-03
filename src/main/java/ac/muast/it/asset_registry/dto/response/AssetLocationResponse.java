// dto/response/AssetLocationResponse.java
package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetLocationResponse {
    private Long id;
    private Long assetId;
    private Long officeId;
    private String officeName;
    private String campusName;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}