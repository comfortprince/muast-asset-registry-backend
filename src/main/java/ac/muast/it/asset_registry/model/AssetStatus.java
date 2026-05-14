package ac.muast.it.asset_registry.model;

public enum AssetStatus {
    AVAILABLE("Available", "Asset is in storage and ready for assignment"),
    ASSIGNED("Assigned", "Currently assigned to a user"),
    ON_LOAN("On Loan", "Temporarily loaned out"),
    IN_REPAIR("In Repair", "Undergoing maintenance or repair"),
    DECOMMISSIONED("Decommissioned", "Retired from service"),
    LOST("Lost", "Reported as lost or missing");

    private final String displayName;
    private final String description;

    AssetStatus(String displayName, String description) {
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