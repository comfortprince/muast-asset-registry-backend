package ac.muast.it.asset_registry.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response format")
public class ErrorResponse {
  
  @Schema(description = "Error title", example = "Validation Error")
  private String title;
  
  @Schema(description = "Detailed error message", example = "Validation failed. Please check the submitted data.")
  private String detail;
  
  @Schema(description = "HTTP status code", example = "400")
  private int status;
  
  @Schema(description = "Application-specific error code", 
          allowableValues = {"VALIDATION_ERROR", "INVALID_CREDENTIALS", "ACCOUNT_LOCKED", "ACCOUNT_DISABLED"},
          example = "VALIDATION_ERROR")
  private String errorCode;
  
  @Schema(description = "Field-specific validation errors (only for VALIDATION_ERROR)")
  private Map<String, String> fieldErrors;
}
