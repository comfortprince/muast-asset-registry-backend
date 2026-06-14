// dto/response/PermissionResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionResponse {
    private String name;
    private String displayName;
    private String description;
    private String module;
}