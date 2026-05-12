// model/GrvEntry.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grv_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrvEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    @Column(name = "quantity_received", nullable = false)
    private Integer quantityReceived;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 2, insertable = false, updatable = false)
    private BigDecimal totalPrice;  // Generated column — read-only

    @Column(name = "storekeeper_remarks", columnDefinition = "TEXT")
    private String storekeeperRemarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storekeeper_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User storekeeper;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "grvEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GrvItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}