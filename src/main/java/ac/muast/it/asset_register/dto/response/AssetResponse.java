// dto/response/AssetResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class AssetResponse {
    private Long id;
    private String code;
    private Long assetCategoryId;
    private String assetCategoryName;
    private String brand;
    private String serialNumber;
    private String status;
    private LocalDate purchaseDate;
    private BigDecimal purchaseCost;
    private String specs;
    private String notes;
    private AssetLocationResponse currentLocation;
    private AssetAssignmentResponse currentAssignment;
    private Set<String> allowedActions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}