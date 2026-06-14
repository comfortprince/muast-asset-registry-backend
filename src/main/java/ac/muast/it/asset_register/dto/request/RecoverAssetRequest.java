package ac.muast.it.asset_register.dto.request;

import lombok.Data;

@Data
public class RecoverAssetRequest {
    private Long targetStatusId;
    private String notes;
}