// dto/response/LoanResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LoanResponse {
    private Long id;
    private Long assetId;
    private String assetCode;
    private String assetBrand;
    private Long loanedToId;
    private String loanedToUsername;
    private Long loanedById;
    private String loanedByUsername;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private String accessories;
    private String signOutSignature;
    private String signInSignature;
    private Boolean isOverdue;
    private Boolean isReturned;
    private String notes;
    private LocalDateTime createdAt;
}