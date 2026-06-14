// service/AssetStatusService.java
package ac.muast.it.asset_register.service;

import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.AssetStatus;
import ac.muast.it.asset_register.repository.AssetStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetStatusService {

    private final AssetStatusRepository assetStatusRepository;

    public AssetStatus getByName(String name) {
        return assetStatusRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + name));
    }

    public AssetStatus getById(Long id) {
        return assetStatusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AssetStatus> getAll() {
        return assetStatusRepository.findAll();
    }
}