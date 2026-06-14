// service/UserService.java
package ac.muast.it.asset_register.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.dto.request.CreateUserRequest;
import ac.muast.it.asset_register.dto.request.UpdateUserRequest;
import ac.muast.it.asset_register.dto.response.UserResponse;
import ac.muast.it.asset_register.exception.ResourceNotFoundException;
import ac.muast.it.asset_register.exception.UserAlreadyExistsException;
import ac.muast.it.asset_register.model.AuditAction;
import ac.muast.it.asset_register.model.Role;
import ac.muast.it.asset_register.model.User;
import ac.muast.it.asset_register.repository.RoleRepository;
import ac.muast.it.asset_register.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    @Transactional
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
                roles.add(role);
            }
        }

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .enabled(request.getEnabled() != null ? request.getEnabled() : true)
            .accountNonLocked(true)
            .accountNonExpired(true)
            .credentialsNonExpired(true)
            .mustChangePassword(true)
            .roles(roles)
            .build();

        User saved = userRepository.save(user);
        auditService.log(AuditAction.CREATED, saved);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return user;
    }

    @Transactional
    @PreAuthorize("hasAuthority('EDIT_USERS')")
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        String oldValues = auditService.toJson(user);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRoles() != null) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        User saved = userRepository.save(user);
        auditService.log(AuditAction.UPDATED, saved, oldValues);
        return mapToResponse(saved);
    }

    @Transactional
    @PreAuthorize("hasAuthority('EDIT_USERS')")
    public void toggleUserStatus(Long id, boolean enabled) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        String oldValues = auditService.toJson(user);
        user.setEnabled(enabled);
        User saved = userRepository.save(user);
        auditService.log(AuditAction.UPDATED, saved, oldValues);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public List<UserResponse> searchUsers(String username) {
        if (username == null || username.isBlank()) {
            return List.of();
        }
        return userRepository.findByUsernameContainingIgnoreCase(username).stream()
            .map(this::mapToResponse)
            .toList();
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .enabled(user.isEnabled())
            .accountNonLocked(user.isAccountNonLocked())
            .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
            .createdAt(user.getCreatedAt())
            .lastLogin(user.getLastLogin())
            .build();
    }
}