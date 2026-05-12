// model/TemporaryLoan.java
package ac.muast.it.asset_registry.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "temporary_loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaned_to_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User loanedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaned_by_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User loanedBy;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;

    @Column(name = "actual_return_date")
    private LocalDate actualReturnDate;

    @Column(columnDefinition = "TEXT")
    private String accessories;       // e.g., "Power cable, HDMI adapter, remote"

    @Column(name = "sign_out_signature", columnDefinition = "TEXT")
    private String signOutSignature;

    @Column(name = "sign_in_signature", columnDefinition = "TEXT")
    private String signInSignature;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper
    public boolean isReturned() {
        return actualReturnDate != null;
    }

    public boolean isOverdue() {
        return !isReturned() && expectedReturnDate != null 
            && LocalDate.now().isAfter(expectedReturnDate);
    }
}