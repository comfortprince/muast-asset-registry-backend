// model/Campus.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String name;              // e.g., "CSC", "MAR", "AIP"

    @Column(name = "display_name", nullable = false, length = 50)
    private String displayName;        // e.g., "Main Campus"

    @Column(columnDefinition = "TEXT")
    private String address;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Office> offices = new ArrayList<>();
}