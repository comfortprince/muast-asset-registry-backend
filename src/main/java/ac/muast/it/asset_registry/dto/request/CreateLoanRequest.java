// dto/request/CreateLoanRequest.java
package ac.muast.it.asset_registry.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateLoanRequest {
    
    @NotBlank(message = "Asset code is required")
    private String assetCode;
    
    @NotBlank(message = "Borrower username is required")
    private String loanedToUsername;
    
    @NotNull(message = "Expected return date is required")
    private LocalDate expectedReturnDate;
    
    private String accessories;
    private String signOutSignature;
    private String notes;
}