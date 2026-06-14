// controller/LoanController.java
package ac.muast.it.asset_register.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ac.muast.it.asset_register.dto.request.CreateLoanRequest;
import ac.muast.it.asset_register.dto.request.ReturnLoanRequest;
import ac.muast.it.asset_register.dto.response.LoanResponse;
import ac.muast.it.asset_register.model.TemporaryLoan;
import ac.muast.it.asset_register.service.LoanService;

import java.security.Principal;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
        @Valid @RequestBody CreateLoanRequest request,
        Principal p
    ) {
        TemporaryLoan loan = loanService.createLoan(request, p.getName());
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(
        @PathVariable Long id,
        @RequestBody(required = false) ReturnLoanRequest request
    ) {
        ReturnLoanRequest req = request != null ? request : new ReturnLoanRequest();
        TemporaryLoan loan = loanService.returnLoan(id, req);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @GetMapping
    public ResponseEntity<Page<LoanResponse>> getAllLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loanService.getAllLoans(pageable).map(this::mapToResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long id) {
        TemporaryLoan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(mapToResponse(loan));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<LoanResponse>> getActiveLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loanService.getActiveLoans(pageable).map(this::mapToResponse));
    }

    @GetMapping("/overdue")
    public ResponseEntity<Page<LoanResponse>> getOverdueLoans(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loanService.getOverdueLoans(pageable).map(this::mapToResponse));
    }

    @GetMapping("/search/byAsset/{assetId}")
    public ResponseEntity<Page<LoanResponse>> getByAsset(
        @PathVariable Long assetId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loanService.getLoansByAsset(assetId, pageable).map(this::mapToResponse));
    }

    @GetMapping("/search/byUser/{userId}")
    public ResponseEntity<Page<LoanResponse>> getByUser(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(loanService.getLoansByUser(userId, pageable).map(this::mapToResponse));
    }

    private LoanResponse mapToResponse(TemporaryLoan loan) {
        return LoanResponse.builder()
            .id(loan.getId())
            .assetId(loan.getAsset().getId())
            .assetCode(loan.getAsset().getCode())
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