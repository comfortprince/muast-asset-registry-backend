// repository/TemporaryLoanRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.TemporaryLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface TemporaryLoanRepository extends JpaRepository<TemporaryLoan, Long> {

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy 
        WHERE t.actualReturnDate IS NULL
    """)
    Page<TemporaryLoan> findActiveLoans(Pageable pageable);

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy 
        WHERE t.actualReturnDate IS NULL 
        AND t.expectedReturnDate < :today
    """)
    Page<TemporaryLoan> findOverdueLoans(@Param("today") LocalDate today, Pageable pageable);

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy 
        WHERE t.asset.id = :assetId 
        ORDER BY t.loanDate DESC
    """)
    Page<TemporaryLoan> findByAssetIdOrderByLoanDateDesc(@Param("assetId") Long assetId, Pageable pageable);

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy 
        WHERE t.loanedTo.id = :userId 
        ORDER BY t.loanDate DESC
    """)
    Page<TemporaryLoan> findByLoanedToIdOrderByLoanDateDesc(@Param("userId") Long userId, Pageable pageable);

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy 
        WHERE t.id = :id
    """)
    Optional<TemporaryLoan> findByIdWithDetails(@Param("id") Long id);

    @Query("""
        SELECT t FROM TemporaryLoan t 
        JOIN FETCH t.asset 
        JOIN FETCH t.loanedTo 
        JOIN FETCH t.loanedBy
    """)
    Page<TemporaryLoan> findAllWithDetails(Pageable pageable);
}