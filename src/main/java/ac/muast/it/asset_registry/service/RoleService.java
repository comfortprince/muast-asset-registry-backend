package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.model.Role;
import ac.muast.it.asset_registry.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role createRole(Role role) {
        log.info("Creating role: {}", role.getName());
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: id " + id));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name.toUpperCase());
    }

    @Transactional
    public void deleteRole(Long id) {
        log.info("Deleting role with ID: {}", id);
        roleRepository.deleteById(id);
    }
}