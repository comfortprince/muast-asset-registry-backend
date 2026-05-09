package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;  // e.g., "ASSET_CREATE"
    
    private String displayName;  // e.g., "Create Asset"
    private String description;
    private String module;  // e.g., "ASSET", "USER", "REPORT"
}