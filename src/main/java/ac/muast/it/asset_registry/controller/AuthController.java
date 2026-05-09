package ac.muast.it.asset_registry.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.muast.it.asset_registry.annotation.LoginOperation;
import ac.muast.it.asset_registry.dto.request.LoginRequest;
import ac.muast.it.asset_registry.dto.response.AuthResponse;
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.security.JwtUtil;
import ac.muast.it.asset_registry.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtUtil jwtUtil;

  @LoginOperation
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    // Create Unauthenticated token with raw credentials
    UsernamePasswordAuthenticationToken unauthenticatedToken 
      = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    
    // Call AuthenticationManager to VERIFY credentials
    authenticationManager.authenticate(unauthenticatedToken);

    User user = userService.getUserByUsername(request.getUsername());

    // Why? - Generate tokens
    String accessToken = jwtUtil.generateAccessToken(user);
    String refreshToken = jwtUtil.generateRefreshToken(user);
      
    // Update last login
    user.setLastLogin(LocalDateTime.now());
    user = userService.updateUser(
      user.getId(),
      user
    );
      
    // Build response
    AuthResponse.UserInfo userInfo = AuthResponse.UserInfo.builder()
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .build();

    AuthResponse response = AuthResponse.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .expiresIn(86400000L) // 24 hours
      .user(userInfo)
      .build();
      
    return ResponseEntity.ok(response);
  }
}
