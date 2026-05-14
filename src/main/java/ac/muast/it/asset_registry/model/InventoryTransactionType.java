// model/InventoryTransactionType.java
package ac.muast.it.asset_registry.model;

public enum InventoryTransactionType {
    RECEIVED("Received", "Stock added to inventory"),
    SENT("Sent", "Issued to department or printer"),
    RETURNED("Returned", "Returned to storage"),
    ADJUSTED("Adjusted", "Manual stock correction"),
    LOST("Lost", "Reported as lost or damaged");

    private final String displayName;
    private final String description;

    InventoryTransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}