package ac.muast.it.asset_register.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asset_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetCategory implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}