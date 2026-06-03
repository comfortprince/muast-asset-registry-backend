// controller/CampusController.java
package ac.muast.it.asset_registry.controller;

import ac.muast.it.asset_registry.dto.request.CreateCampusRequest;
import ac.muast.it.asset_registry.dto.request.CreateOfficeRequest;
import ac.muast.it.asset_registry.dto.response.CampusResponse;
import ac.muast.it.asset_registry.dto.response.OfficeResponse;
import ac.muast.it.asset_registry.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campuses")
@RequiredArgsConstructor
@Validated
public class CampusController {

    private final LocationService locationService;

    // =============================================
    // CAMPUSES
    // =============================================

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<CampusResponse> createCampus(@Valid @RequestBody CreateCampusRequest request) {
        return ResponseEntity.ok(locationService.createCampus(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_LOCATIONS')")
    public ResponseEntity<List<CampusResponse>> getAllCampuses() {
        return ResponseEntity.ok(locationService.getAllCampuses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_LOCATIONS')")
    public ResponseEntity<CampusResponse> getCampus(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getCampusById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<CampusResponse> updateCampus(
        @PathVariable Long id,
        @Valid @RequestBody CreateCampusRequest request
    ) {
        return ResponseEntity.ok(locationService.updateCampus(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        locationService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }

    // =============================================
    // OFFICES
    // =============================================

    @PostMapping("/{campusId}/offices")
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<OfficeResponse> createOffice(
        @PathVariable Long campusId,
        @Valid @RequestBody CreateOfficeRequest request
    ) {
        return ResponseEntity.ok(locationService.createOffice(campusId, request));
    }

    @GetMapping("/{campusId}/offices")
    @PreAuthorize("hasAuthority('READ_LOCATIONS')")
    public ResponseEntity<Page<OfficeResponse>> getOffices(
        @PathVariable Long campusId,
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(locationService.getOfficesByCampus(campusId, pageable));
    }

    @GetMapping("/{campusId}/offices/{officeId}")
    @PreAuthorize("hasAuthority('READ_LOCATIONS')")
    public ResponseEntity<OfficeResponse> getOffice(
        @PathVariable Long campusId,
        @PathVariable Long officeId
    ) {
        return ResponseEntity.ok(locationService.getOfficeById(campusId, officeId));
    }

    @PutMapping("/{campusId}/offices/{officeId}")
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<OfficeResponse> updateOffice(
        @PathVariable Long campusId,
        @PathVariable Long officeId,
        @Valid @RequestBody CreateOfficeRequest request
    ) {
        return ResponseEntity.ok(locationService.updateOffice(campusId, officeId, request));
    }

    @DeleteMapping("/{campusId}/offices/{officeId}")
    @PreAuthorize("hasAuthority('MANAGE_LOCATIONS')")
    public ResponseEntity<Void> deleteOffice(
        @PathVariable Long campusId,
        @PathVariable Long officeId
    ) {
        locationService.deleteOffice(campusId, officeId);
        return ResponseEntity.noContent().build();
    }
}