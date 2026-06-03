// controller/AssetAssignmentController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.response.AssetAssignmentResponse;
import ac.muast.it.asset_registry.model.AssetHistory;
import ac.muast.it.asset_registry.repository.AssetAssignmentHistoryRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets/assetAssignments")
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class AssetAssignmentController {

    private final AssetAssignmentHistoryRepository assignmentHistoryRepository;

    @GetMapping("")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetAssignmentResponse> getAssetAssignments(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetAssignmentResponse> assignments = assignmentHistoryRepository
            .findAll(pageable)
            .map(aa -> AssetAssignmentResponse.builder()
                .id(aa.getId())
                .assetId(aa.getAsset().getId())
                .userId(aa.getUser().getId())
                .username(aa.getUser().getUsername())
                .roleAtAssignment(aa.getRoleAtAssignment())
                .notes(aa.getNotes())
                .validFrom(aa.getValidFrom())
                .validTo(aa.getValidTo())
                .build()
            );
        return new PagedModel<>(assignments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<AssetAssignmentResponse> getById(@PathVariable Long id) {
        return assignmentHistoryRepository.findById(id)
            .map(aa -> AssetAssignmentResponse.builder()
                .id(aa.getId())
                .assetId(aa.getAsset().getId())
                .userId(aa.getUser().getId())
                .username(aa.getUser().getUsername())
                .roleAtAssignment(aa.getRoleAtAssignment())
                .notes(aa.getNotes())
                .validFrom(aa.getValidFrom())
                .validTo(aa.getValidTo())
                .build())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/byAsset/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetAssignmentResponse> getByAssetId(
        @PathVariable Long assetId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetAssignmentResponse> assignments = assignmentHistoryRepository
            .findByAssetIdOrderByValidFromDesc(assetId, pageable)
            .map(aa -> AssetAssignmentResponse.builder()
                .id(aa.getId())
                .userId(aa.getUser().getId())
                .username(aa.getUser().getUsername())
                .roleAtAssignment(aa.getRoleAtAssignment())
                .notes(aa.getNotes())
                .validFrom(aa.getValidFrom())
                .validTo(aa.getValidTo())
                .build()
            );
        return new PagedModel<>(assignments);
    }

    @GetMapping("/search/byUser/{userId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public PagedModel<AssetAssignmentResponse> getByUserId(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AssetAssignmentResponse> assignments = assignmentHistoryRepository
            .findByUserIdOrderByValidFromDesc(userId, pageable)
            .map(aa -> AssetAssignmentResponse.builder()
                .id(aa.getId())
                .assetId(aa.getAsset().getId())
                .userId(aa.getUser().getId())
                .username(aa.getUser().getUsername())
                .roleAtAssignment(aa.getRoleAtAssignment())
                .notes(aa.getNotes())
                .validFrom(aa.getValidFrom())
                .validTo(aa.getValidTo())
                .build()
            );
        return new PagedModel<>(assignments);
    }

    @GetMapping("/search/current/{assetId}")
    @PreAuthorize("hasAuthority('READ_ASSETS')")
    public ResponseEntity<AssetAssignmentResponse> getCurrentAssignment(@PathVariable Long assetId) {
        return assignmentHistoryRepository.findCurrentByAssetId(assetId, AssetHistory.MAX_VALID_TO)
            .map(aa -> AssetAssignmentResponse.builder()
                .id(aa.getId())
                .assetId(aa.getAsset().getId())
                .userId(aa.getUser().getId())
                .username(aa.getUser().getUsername())
                .roleAtAssignment(aa.getRoleAtAssignment())
                .notes(aa.getNotes())
                .validFrom(aa.getValidFrom())
                .validTo(aa.getValidTo())
                .build())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}