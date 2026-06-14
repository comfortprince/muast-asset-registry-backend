// service/RoleService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.CreateRoleRequest;
import ac.muast.it.asset_register.dto.request.UpdateRolePermissionsRequest;
import ac.muast.it.asset_register.dto.response.PermissionResponse;
import ac.muast.it.asset_register.dto.response.RoleResponse;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.model.Permission;
import ac.muast.it.asset_register.model.Role;
import ac.muast.it.asset_register.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        Set<Permission> permissions = resolvePermissions(request.getPermissions());

        Role role = Role.builder()
            .name(request.getName())
            .description(request.getDescription())
            .permissions(permissions)
            .build();

        return mapToResponse(roleRepository.save(role));
    }

    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::mapToResponse);
    }

    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));
        return mapToResponse(role);
    }

    @Transactional
    public RoleResponse updateRole(Long id, CreateRoleRequest request) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + id));

        role.setDescription(request.getDescription());

        if (request.getPermissions() != null) {
            role.setPermissions(resolvePermissions(request.getPermissions()));
        }

        return mapToResponse(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Set<PermissionResponse> getRolePermissions(Long roleId) {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleId));
        return role.getPermissions().stream()
            .map(this::mapPermissionToResponse)
            .collect(Collectors.toSet());
    }

    @Transactional
    public RoleResponse updateRolePermissions(Long roleId, UpdateRolePermissionsRequest request) {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleId));

        role.setPermissions(resolvePermissions(request.getPermissions()));
        return mapToResponse(roleRepository.save(role));
    }

    @Transactional
    public void removeRolePermission(Long roleId, String permissionName) {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleId));
        role.getPermissions().remove(Permission.valueOf(permissionName));
        roleRepository.save(role);
    }

    public List<String> getRoleNames() {
        return roleRepository.findAll().stream()
            .map(Role::getName)
            .toList();
    }

    public Set<PermissionResponse> getAllPermissions() {
        return Set.of(Permission.values()).stream()
            .map(this::mapPermissionToResponse)
            .collect(Collectors.toSet());
    }

    private Set<Permission> resolvePermissions(Set<String> permissionNames) {
        Set<Permission> permissions = new HashSet<>();
        if (permissionNames != null) {
            for (String name : permissionNames) {
                permissions.add(Permission.valueOf(name));
            }
        }
        return permissions;
    }

    private RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .permissions(role.getPermissions().stream()
                .map(Permission::name)
                .collect(Collectors.toSet()))
            .build();
    }

    private PermissionResponse mapPermissionToResponse(Permission perm) {
        return PermissionResponse.builder()
            .name(perm.name())
            .displayName(perm.getDisplayName())
            .description(perm.getDescription())
            .module(perm.getModule().name())
            .build();
    }
}