// controller/OfficeController.java
package ac.muast.it.asset_register.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ac.muast.it.asset_register.dto.response.OfficeResponse;
import ac.muast.it.asset_register.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final LocationService locationService;

    @GetMapping("/search")
    public ResponseEntity<List<OfficeResponse>> searchOffices(
        @RequestParam(required = false) Long campusId,
        @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(locationService.searchOffices(campusId, name));
    }
}