// controller/OfficeController.java
package ac.muast.it.asset_registry.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ac.muast.it.asset_registry.dto.response.OfficeResponse;
import ac.muast.it.asset_registry.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final LocationService locationService;

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('READ_LOCATIONS')")
    public ResponseEntity<List<OfficeResponse>> searchOffices(
        @RequestParam(required = false) Long campusId,
        @RequestParam(required = false, name = "display_name") String displayName
    ) {
        return ResponseEntity.ok(locationService.searchOffices(campusId, displayName));
    }
}