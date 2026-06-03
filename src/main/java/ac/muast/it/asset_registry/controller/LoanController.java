// controller/LoanController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.CreateLoanRequest;
import ac.muast.it.asset_registry.dto.request.ReturnLoanRequest;
import ac.muast.it.asset_registry.dto.response.LoanResponse;
import ac.muast.it.asset_registry.model.TemporaryLoan;
import ac.muast.it.asset_registry.repository.TemporaryLoanRepository;
import ac.muast.it.asset_registry.service.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanService loanService;
    private final TemporaryLoanRepository loanRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<LoanResponse> createLoan(
        @Valid @RequestBody CreateLoanRequest request,
        Principal p
    ) {
        TemporaryLoan loan = loanService.createLoan(request, p.getName());
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasAuthority('MANAGE_ASSETS')")
    public ResponseEntity<LoanResponse> returnLoan(
        @PathVariable Long id,
        @RequestBody(required = false) ReturnLoanRequest request
    ) {
        ReturnLoanRequest req = request != null ? request : new ReturnLoanRequest();
        TemporaryLoan loan = loanService.returnLoan(id, req);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public PagedModel<LoanResponse> getAllLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new PagedModel<>(loanRepository.findAllWithDetails(pageable).map(this::mapToResponse));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {
        return loanRepository.findByIdWithDetails(id)
            .map(this::mapToResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public PagedModel<LoanResponse> getActiveLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new PagedModel<>(loanRepository.findActiveLoans(pageable).map(this::mapToResponse));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public PagedModel<LoanResponse> getOverdueLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new PagedModel<>(loanRepository.findOverdueLoans(LocalDate.now(), pageable).map(this::mapToResponse));
    }

    @GetMapping("/search/byAsset/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public PagedModel<LoanResponse> getByAsset(
        @PathVariable Long assetId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new PagedModel<>(loanRepository.findByAssetIdOrderByLoanDateDesc(assetId, pageable).map(this::mapToResponse));
    }

    @GetMapping("/search/byUser/{userId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    @Transactional(readOnly = true)
    public PagedModel<LoanResponse> getByUser(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new PagedModel<>(loanRepository.findByLoanedToIdOrderByLoanDateDesc(userId, pageable).map(this::mapToResponse));
    }

    private LoanResponse mapToResponse(TemporaryLoan loan) {
        return LoanResponse.builder()
            .id(loan.getId())
            .assetId(loan.getAsset().getId())
            .assetCode(loan.getAsset().getCode())         // ← getCode
            .assetBrand(loan.getAsset().getBrand())
            .loanedToId(loan.getLoanedTo().getId())
            .loanedToUsername(loan.getLoanedTo().getUsername())
            .loanedById(loan.getLoanedBy().getId())
            .loanedByUsername(loan.getLoanedBy().getUsername())
            .loanDate(loan.getLoanDate())
            .expectedReturnDate(loan.getExpectedReturnDate())
            .actualReturnDate(loan.getActualReturnDate())
            .accessories(loan.getAccessories())
            .signOutSignature(loan.getSignOutSignature())
            .signInSignature(loan.getSignInSignature())
            .isOverdue(loan.isOverdue())
            .isReturned(loan.isReturned())
            .notes(loan.getNotes())
            .createdAt(loan.getCreatedAt())
            .build();
    }
}