// dto/response/CampusResponse.java
package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CampusResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String address;
    private List<OfficeResponse> offices;
}