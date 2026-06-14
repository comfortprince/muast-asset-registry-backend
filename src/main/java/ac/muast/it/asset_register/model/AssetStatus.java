// model/AssetStatus.java
package ac.muast.it.asset_register.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asset_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetStatus {
    public static final String AVAILABLE = "AVAILABLE";
    public static final String ASSIGNED = "ASSIGNED";
    public static final String ON_LOAN = "ON_LOAN";
    public static final String IN_REPAIR = "IN_REPAIR";
    public static final String DECOMMISSIONED = "DECOMMISSIONED";
    public static final String LOST = "LOST";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

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

    public Set<String> getAllowedActions() {
        return switch (name) {
            case "AVAILABLE" -> Set.of(Action.ASSIGN, Action.TRANSFER, Action.REPAIR, 
               Action.LOST, Action.DECOMMISSION);
            case "ASSIGNED" -> Set.of(Action.CHECKIN, Action.TRANSFER, Action.REPAIR, 
               Action.LOST, Action.DECOMMISSION);
            case "ON_LOAN" -> Set.of(Action.CHECKIN, Action.LOST);
            case "IN_REPAIR" -> Set.of(Action.CHECKIN, Action.LOST, Action.DECOMMISSION);
            case "DECOMMISSIONED" -> Set.of(Action.RECOVER);
            case "LOST" -> Set.of(Action.RECOVER);
            default -> Set.of();
        };
    }

    public boolean canPerformAction(String action) {
        return getAllowedActions().contains(action);
    }
}