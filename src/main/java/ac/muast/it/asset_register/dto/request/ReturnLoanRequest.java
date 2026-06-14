// dto/request/ReturnLoanRequest.java
package ac.muast.it.asset_register.dto.request;

import lombok.Data;

@Data
public class ReturnLoanRequest {
    private String signInSignature;
    private String notes;
}