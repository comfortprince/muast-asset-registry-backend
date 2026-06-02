// model/ConsumableTransaction.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumable_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumableTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumable_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Consumable consumable;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "transaction_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ConsumableTransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_office_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office sourceOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_office_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office destinationOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(columnDefinition = "TEXT")
    private String notes;
}