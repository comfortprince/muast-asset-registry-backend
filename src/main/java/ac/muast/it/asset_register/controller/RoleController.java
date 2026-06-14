// controller/RoleController.java
package ac.muast.it.asset_register.controller;

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

import ac.muast.it.asset_register.dto.request.CreateRoleRequest;
import ac.muast.it.asset_register.dto.request.UpdateRolePermissionsRequest;
import ac.muast.it.asset_register.dto.response.PermissionResponse;
import ac.muast.it.asset_register.dto.response.RoleResponse;
import ac.muast.it.asset_register.service.RoleService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<Page<RoleResponse>> getAllRoles(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @GetMapping("/names")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<List<String>> getRoleNames() {
        return ResponseEntity.ok(roleService.getRoleNames());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    public ResponseEntity<RoleResponse> updateRole(
        @PathVariable Long id,
        @Valid @RequestBody CreateRoleRequest request
    ) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<Set<PermissionResponse>> getRolePermissions(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRolePermissions(id));
    }

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    public ResponseEntity<RoleResponse> updateRolePermissions(
        @PathVariable Long id,
        @RequestBody UpdateRolePermissionsRequest request
    ) {
        return ResponseEntity.ok(roleService.updateRolePermissions(id, request));
    }

    @DeleteMapping("/{id}/permissions/{permissionName}")
    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    public ResponseEntity<Void> removeRolePermission(
        @PathVariable Long id,
        @PathVariable String permissionName
    ) {
        roleService.removeRolePermission(id, permissionName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permissions/all")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<Set<PermissionResponse>> getAllPermissions() {
        return ResponseEntity.ok(roleService.getAllPermissions());
    }
}