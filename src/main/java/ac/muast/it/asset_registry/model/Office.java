// model/Office.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "offices", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"campus_id", "name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;              // e.g., "IT_OFFICE_1"

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;        // e.g., "IT Office 1"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Campus campus;
}