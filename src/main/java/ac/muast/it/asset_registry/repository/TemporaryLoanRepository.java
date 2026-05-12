// repository/TemporaryLoanRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.TemporaryLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TemporaryLoanRepository extends JpaRepository<TemporaryLoan, Long> {
    List<TemporaryLoan> findByAssetIdOrderByLoanDateDesc(Long assetId);
    List<TemporaryLoan> findByLoanedToId(Long userId);
    
    @Query("SELECT t FROM TemporaryLoan t WHERE t.actualReturnDate IS NULL")
    List<TemporaryLoan> findActiveLoans();
    
    @Query("SELECT t FROM TemporaryLoan t WHERE t.actualReturnDate IS NULL AND t.asset.id = :assetId")
    Optional<TemporaryLoan> findActiveLoanByAssetId(Long assetId);
    
    @Query("SELECT t FROM TemporaryLoan t WHERE t.actualReturnDate IS NULL AND t.expectedReturnDate < :date")
    List<TemporaryLoan> findOverdueLoans(LocalDate date);
}