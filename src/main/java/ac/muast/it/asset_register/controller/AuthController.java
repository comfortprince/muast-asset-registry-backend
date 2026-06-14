package ac.muast.it.asset_register.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.muast.it.asset_register.annotation.LoginOperation;
import ac.muast.it.asset_register.dto.response.AuthResponse;
import ac.muast.it.asset_register.model.User;
import ac.muast.it.asset_register.security.UserAuthRepository;
import ac.muast.it.asset_register.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final JwtService jwtService;
  private final UserAuthRepository userAuthRepository;

  @LoginOperation
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(Authentication authentication, Principal p) {
    User user = userAuthRepository.findByUsername(p.getName()).orElseThrow();
    long expirationTime = 60; // minutes
    String jwt = jwtService.generateToken(authentication, expirationTime);

    user.setLastLogin(LocalDateTime.now());
    user = userAuthRepository.save(user);

    AuthResponse.UserInfo userInfo = AuthResponse.UserInfo.builder()
      .id(user.getId())
      .username(user.getUsername())
      .email(user.getEmail())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .build();

    AuthResponse response = AuthResponse.builder()
      .accessToken(jwt)
      .expiresIn(expirationTime)
      .user(userInfo)
      .build();
    
    return ResponseEntity.ok(response);
  }
}
