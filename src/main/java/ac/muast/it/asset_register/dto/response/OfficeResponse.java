// dto/response/OfficeResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfficeResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean isActive;
    private Long campusId;
    private String campusName;
}