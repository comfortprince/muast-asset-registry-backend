// model/ConsumableTransactionType.java
package ac.muast.it.asset_registry.model;

public enum ConsumableTransactionType {
    RECEIVED("Received", "Stock added to inventory"),
    TRANSFERRED("Transferred", "Moved between offices"),
    ADJUSTED("Adjusted", "Manual stock correction"),
    CONSUMED("Consumed", "Used by an asset (e.g., toner in a printer)");

    private final String displayName;
    private final String description;

    ConsumableTransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}