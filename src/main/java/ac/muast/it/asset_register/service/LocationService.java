// service/LocationService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.CreateCampusRequest;
import ac.muast.it.asset_register.dto.request.CreateOfficeRequest;
import ac.muast.it.asset_register.dto.response.CampusResponse;
import ac.muast.it.asset_register.dto.response.OfficeResponse;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.Campus;
import ac.muast.it.asset_register.model.Office;
import ac.muast.it.asset_register.repository.CampusRepository;
import ac.muast.it.asset_register.repository.OfficeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final CampusRepository campusRepository;
    private final OfficeRepository officeRepository;

    // =============================================
    // CAMPUSES
    // =============================================

    @Transactional
    public CampusResponse createCampus(CreateCampusRequest request) {
        Campus campus = Campus.builder()
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .address(request.getAddress())
            .build();
        return mapCampusToResponse(campusRepository.save(campus));
    }

    @Transactional(readOnly = true)
    public List<CampusResponse> getAllCampuses() {
        return campusRepository.findAll().stream()
        .map(this::mapCampusToResponse)
        .toList();
    }

    @Transactional(readOnly = true)
    public CampusResponse getCampusById(Long id) {
        Campus campus = campusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campus not found: " + id));
        return mapCampusToResponse(campus);
    }

    @Transactional
    public CampusResponse updateCampus(Long id, CreateCampusRequest request) {
        Campus campus = campusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campus not found: " + id));
        campus.setName(request.getName());
        campus.setDescription(request.getDescription());
        campus.setAddress(request.getAddress());
        return mapCampusToResponse(campusRepository.save(campus));
    }

    @Transactional
    public void deleteCampus(Long id) {
        if (officeRepository.existsByCampusId(id)) {
            throw new IllegalArgumentException("Cannot delete campus with associated offices.");
        }
        campusRepository.deleteById(id);
    }

    // =============================================
    // OFFICES
    // =============================================

    @Transactional
    public OfficeResponse createOffice(Long campusId, CreateOfficeRequest request) {
        Campus campus = campusRepository.findById(campusId)
            .orElseThrow(() -> new ResourceNotFoundException("Campus not found: " + campusId));

        Office office = Office.builder()
            .code(request.getCode())
            .name(request.getName())
            .description(request.getDescription())
            .isActive(request.getIsActive() != null ? request.getIsActive() : true)
            .campus(campus)
            .build();
        return mapOfficeToResponse(officeRepository.save(office));
    }
    
    @Transactional(readOnly = true)
    public Page<OfficeResponse> getOfficesByCampus(Long campusId, Pageable pageable) {
        return officeRepository.findByCampusId(campusId, pageable).map(this::mapOfficeToResponse);
    }

    @Transactional(readOnly = true)
    public OfficeResponse getOfficeById(Long campusId, Long officeId) {
        Office office = officeRepository.findById(officeId)
            .orElseThrow(() -> new ResourceNotFoundException("Office not found: " + officeId));
        return mapOfficeToResponse(office);
    }

    @Transactional
    public OfficeResponse updateOffice(Long campusId, Long officeId, CreateOfficeRequest request) {
        Office office = officeRepository.findById(officeId)
            .orElseThrow(() -> new ResourceNotFoundException("Office not found: " + officeId));

        office.setName(request.getName());
        office.setDescription(request.getDescription());
        if (request.getIsActive() != null) {
            office.setIsActive(request.getIsActive());
        }
        return mapOfficeToResponse(officeRepository.save(office));
    }

    @Transactional
    public void deleteOffice(Long campusId, Long officeId) {
        officeRepository.deleteById(officeId);
    }

    @Transactional(readOnly = true)
    public List<OfficeResponse> searchOffices(Long campusId, String name) {
        List<Office> offices = List.of();
        
        if (campusId != null && name != null && !name.isBlank()) {
            offices = officeRepository.findByCampusIdAndNameContainingIgnoreCase(campusId, name);
        } else if (campusId != null) {
            offices = officeRepository.findByCampusId(campusId, Pageable.unpaged()).getContent();
        } else if (name != null && !name.isBlank()) {
            offices = officeRepository.findByNameContainingIgnoreCase(name);
        }
        
        return offices.stream()
            .map(this::mapOfficeToResponse)
            .toList();
    }

    // =============================================
    // MAPPERS
    // =============================================

    private CampusResponse mapCampusToResponse(Campus campus) {
        return CampusResponse.builder()
            .id(campus.getId())
            .code(campus.getCode())
            .name(campus.getName())
            .description(campus.getDescription())
            .address(campus.getAddress())
            .numOfOffices(campus.getOffices() != null ? campus.getOffices().size() : 0)
            .build();
    }

    private OfficeResponse mapOfficeToResponse(Office office) {
        return OfficeResponse.builder()
            .id(office.getId())
            .code(office.getCode())
            .name(office.getName())
            .description(office.getDescription())
            .isActive(office.getIsActive())
            .campusId(office.getCampus() != null ? office.getCampus().getId() : null)
            .campusName(office.getCampus() != null ? office.getCampus().getName() : null)
            .build();
    }
}