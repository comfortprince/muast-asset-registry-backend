// model/InventoryTransaction.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private InventoryItem inventoryItem;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 20)
    private InventoryTransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_location_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office fromLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office toLocation;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User issuedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User receivedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}