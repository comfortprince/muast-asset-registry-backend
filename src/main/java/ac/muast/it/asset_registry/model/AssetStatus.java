// model/AssetStatus.java
package ac.muast.it.asset_registry.model;

import java.util.Set;

public enum AssetStatus {
    AVAILABLE("Available", "Asset is in storage and ready for assignment",
        Set.of(Action.ASSIGN, Action.TRANSFER, Action.REPAIR, 
               Action.LOST, Action.DECOMMISSION)),
    
    ASSIGNED("Assigned", "Currently assigned to a user",
        Set.of(Action.CHECKIN, Action.TRANSFER, Action.REPAIR, 
               Action.LOST, Action.DECOMMISSION)),
    
    ON_LOAN("On Loan", "Temporarily loaned out",
        Set.of(Action.CHECKIN, Action.LOST)),
    
    IN_REPAIR("In Repair", "Undergoing maintenance or repair",
        Set.of(Action.CHECKIN, Action.LOST, Action.DECOMMISSION)),
    
    DECOMMISSIONED("Decommissioned", "Retired from service", 
        Set.of(Action.RECOVER)),
    
    LOST("Lost", "Reported as lost or missing", 
        Set.of(Action.RECOVER));

    private final String displayName;
    private final String description;
    private final Set<String> allowedActions;

    AssetStatus(String displayName, String description, Set<String> allowedActions) {
        this.displayName = displayName;
        this.description = description;
        this.allowedActions = allowedActions;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public Set<String> getAllowedActions() { return allowedActions; }
    
    public boolean canPerform(String action) {
        return allowedActions.contains(action);
    }

    // Action constants
    public static class Action {
        public static final String ASSIGN = "assign";
        public static final String TRANSFER = "transfer";
        public static final String CHECKIN = "checkin";
        public static final String REPAIR = "repair";
        public static final String LOST = "lost";
        public static final String DECOMMISSION = "decommission";
        public static final String RECOVER = "recover";
        
        private Action() {}
    }
}