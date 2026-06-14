// model/Campus.java
package ac.muast.it.asset_register.model;

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
    private String code;              // e.g., "CSC", "MAR", "AIP"

    @Column(unique = true, nullable = false, length = 50)
    private String name;              // e.g., "Main Campus"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String address;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Office> offices = new ArrayList<>();
}