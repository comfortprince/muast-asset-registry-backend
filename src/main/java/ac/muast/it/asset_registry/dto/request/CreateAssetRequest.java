// dto/request/CreateAssetRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateAssetRequest {
    
    @NotBlank(message = "Asset code is required")
    private String code;
    
    @NotNull(message = "Asset type ID is required")
    private Long assetTypeId;
    
    private Long grvEntryId;
    private String brand;
    private String serialNumber;
    private LocalDate purchaseDate;
    private BigDecimal purchaseCost;
    private String specs;
    private String notes;
}