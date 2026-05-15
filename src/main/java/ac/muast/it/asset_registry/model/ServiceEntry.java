// model/ServiceEntry.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grv_entry_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GrvEntry grvEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @Column(name = "service_type", nullable = false, length = 50)
    private String serviceType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String vendor;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "date_performed", nullable = false)
    private LocalDate datePerformed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User performedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}