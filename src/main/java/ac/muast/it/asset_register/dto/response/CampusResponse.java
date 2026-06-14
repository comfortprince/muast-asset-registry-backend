// dto/response/CampusResponse.java
package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampusResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String address;
    private Integer numOfOffices;
}