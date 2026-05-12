// model/Status.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String name;              // e.g., "AVAILABLE", "IN_USE", "IN_REPAIR"

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;        // e.g., "Available", "In Use"

    @Column(columnDefinition = "TEXT")
    private String description;
}