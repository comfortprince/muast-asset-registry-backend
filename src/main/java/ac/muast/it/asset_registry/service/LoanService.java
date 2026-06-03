// service/LoanService.java
package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.request.CreateLoanRequest;
import ac.muast.it.asset_registry.dto.request.ReturnLoanRequest;
import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.model.*;
import ac.muast.it.asset_registry.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final TemporaryLoanRepository loanRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    @Transactional
    public TemporaryLoan createLoan(CreateLoanRequest request, String loanedByUsername) {
        // Find asset
        Asset asset = assetRepository.findByCode(request.getAssetCode())
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + request.getAssetCode()));

        // Validate asset is available
        if (asset.getCurrentStatus() != AssetStatus.AVAILABLE) {
            throw new IllegalStateException("Asset must be AVAILABLE to loan. Current status: " + asset.getCurrentStatus());
        }

        // Find users
        User loanedTo = userRepository.findByUsername(request.getLoanedToUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getLoanedToUsername()));
        User loanedBy = userRepository.findByUsername(loanedByUsername)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + loanedByUsername));

        // Create loan
        TemporaryLoan loan = TemporaryLoan.builder()
            .asset(asset)
            .loanedTo(loanedTo)
            .loanedBy(loanedBy)
            .loanDate(LocalDate.now())
            .expectedReturnDate(request.getExpectedReturnDate())
            .accessories(request.getAccessories())
            .signOutSignature(request.getSignOutSignature())
            .notes(request.getNotes())
            .build();
        loan = loanRepository.save(loan);

        // Update asset status
        asset.setCurrentStatus(AssetStatus.ON_LOAN);
        assetRepository.save(asset);

        log.info("Asset {} loaned to {} by {}, due {}", 
            request.getAssetCode(), request.getLoanedToUsername(), loanedByUsername, request.getExpectedReturnDate());
        return loan;
    }

    @Transactional
    public TemporaryLoan returnLoan(Long loanId, ReturnLoanRequest request) {
        TemporaryLoan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + loanId));

        if (loan.getActualReturnDate() != null) {
            throw new IllegalStateException("Loan already returned on " + loan.getActualReturnDate());
        }

        loan.setActualReturnDate(LocalDate.now());
        loan.setSignInSignature(request.getSignInSignature());
        if (request.getNotes() != null) loan.setNotes(request.getNotes());
        loan = loanRepository.save(loan);

        // Update asset status back to available
        Asset asset = loan.getAsset();
        asset.setCurrentStatus(AssetStatus.AVAILABLE);
        assetRepository.save(asset);

        log.info("Asset {} returned from loan by {}", asset.getCode(), loan.getLoanedTo().getUsername());
        return loan;
    }
}