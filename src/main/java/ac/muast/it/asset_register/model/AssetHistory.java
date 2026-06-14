// model/AssetHistory.java
package ac.muast.it.asset_register.model;

import java.time.LocalDateTime;

public final class AssetHistory {
    public static final LocalDateTime MAX_VALID_TO = LocalDateTime.of(9000, 1, 1, 0, 0);
    
    private AssetHistory() {}
}