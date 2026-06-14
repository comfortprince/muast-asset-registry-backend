// dto/request/UpdateRolePermissionsRequest.java
package ac.muast.it.asset_register.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class UpdateRolePermissionsRequest {
    private Set<String> permissions;
}