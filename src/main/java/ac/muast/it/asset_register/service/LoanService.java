// service/LoanService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.CreateLoanRequest;
import ac.muast.it.asset_register.dto.request.ReturnLoanRequest;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.*;
import ac.muast.it.asset_register.repository.*;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final TemporaryLoanRepository loanRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetStatusService assetStatusService;

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public TemporaryLoan createLoan(CreateLoanRequest request, String loanedByUsername) {
        Asset asset = assetRepository.findByCode(request.getAssetCode())
            .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + request.getAssetCode()));

        if (!asset.getCurrentStatus().getName().equals(AssetStatus.AVAILABLE)) {
            throw new IllegalStateException("Asset must be AVAILABLE to loan. Current status: " + asset.getCurrentStatus().getName());
        }

        User loanedTo = userRepository.findByUsername(request.getLoanedToUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getLoanedToUsername()));
        User loanedBy = userRepository.findByUsername(loanedByUsername)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + loanedByUsername));

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

        asset.setCurrentStatus(assetStatusService.getByName(AssetStatus.ON_LOAN));
        assetRepository.save(asset);

        log.info("Asset {} loaned to {} by {}, due {}",
            request.getAssetCode(), request.getLoanedToUsername(), loanedByUsername, request.getExpectedReturnDate());
        return loan;
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
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

        Asset asset = loan.getAsset();
        asset.setCurrentStatus(assetStatusService.getByName(AssetStatus.AVAILABLE));
        assetRepository.save(asset);

        log.info("Asset {} returned from loan by {}", asset.getCode(), loan.getLoanedTo().getUsername());
        return loan;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public Page<TemporaryLoan> getAllLoans(Pageable pageable) {
        return loanRepository.findAllWithDetails(pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public TemporaryLoan getLoanById(Long id) {
        return loanRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found: " + id));
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public Page<TemporaryLoan> getActiveLoans(Pageable pageable) {
        return loanRepository.findActiveLoans(pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public Page<TemporaryLoan> getOverdueLoans(Pageable pageable) {
        return loanRepository.findOverdueLoans(LocalDate.now(), pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public Page<TemporaryLoan> getLoansByAsset(Long assetId, Pageable pageable) {
        return loanRepository.findByAssetIdOrderByLoanDateDesc(assetId, pageable);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public Page<TemporaryLoan> getLoansByUser(Long userId, Pageable pageable) {
        return loanRepository.findByLoanedToIdOrderByLoanDateDesc(userId, pageable);
    }
}