// model/Role.java
package ac.muast.it.asset_register.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;  // e.g., "ADMIN", "IT_STAFF", "VIEWER", "USER"
    
    private String description;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "permission_name")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();
}