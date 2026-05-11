package ac.muast.it.asset_registry.service;

import ac.muast.it.asset_registry.dto.response.UserResponse;
import ac.muast.it.asset_registry.dto.request.UserRegistrationRequest;
import ac.muast.it.asset_registry.exception.ResourceNotFoundException;
import ac.muast.it.asset_registry.exception.UserAlreadyExistsException;
import ac.muast.it.asset_registry.model.Role;
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.repository.RoleRepository;
import ac.muast.it.asset_registry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserRegistrationRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken: " + request.getUsername());
        }

        // Get roles
        Set<Role> roles = new HashSet<>();
        for (String roleName : request.getRoleNames()) {
            Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));
            roles.add(role);
        }

        // Build user
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .roles(roles)
            .enabled(true)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .mustChangePassword(true)
            .build();

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return user;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .toList();
    }

    @Transactional
    public User updateUser(Long id, User user) {
      userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

      return userRepository.save(user);
    }

    @Transactional
    public void updateLastLogin(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false);
        userRepository.save(user);
    }

    @Transactional
    public void toggleUserStatus(Long id, boolean enabled) {
      User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
      user.setEnabled(enabled);
      userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
      userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
      userRepository.deleteById(id);;
    }

    @Transactional
    public void disableUser(Long id){
        toggleUserStatus(id, false);
    }

    public List<User> getUsersByRole(String roleName) {
      return userRepository.findAll()
        .stream()
        .filter(user -> user.getRoles().stream()
          .anyMatch(role -> role.getName().equalsIgnoreCase(roleName)))
        .toList();
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .roles(user.getRoles().stream().map(Role::getName).toList())
            .enabled(user.isEnabled())
            .mustChangePassword(user.isMustChangePassword())
            .lastLogin(user.getLastLogin())
            .createdAt(user.getCreatedAt())
            .build();
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}