package ac.muast.it.asset_registry.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ac.muast.it.asset_registry.dto.request.LoginRequest;
import ac.muast.it.asset_registry.exception.ErrorCodes;
import ac.muast.it.asset_registry.exception.GlobalExceptionHandler;
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.security.JwtUtil;
import ac.muast.it.asset_registry.service.UserService;

class AuthControllerUnitTest {
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private AuthenticationManager authenticationManager;
  private UserService userService;
  private JwtUtil jwtUtil;
  private AuthController authController;

  @BeforeEach
  void setUp() {
    // Create mocks
    authenticationManager = Mockito.mock(AuthenticationManager.class);
    userService = Mockito.mock(UserService.class);
    jwtUtil = Mockito.mock(JwtUtil.class);
    objectMapper = new ObjectMapper();

    // Create controller with mocks
    authController = new AuthController(authenticationManager, userService, jwtUtil);
    mockMvc = MockMvcBuilders
      .standaloneSetup(authController)
      .setControllerAdvice(new GlobalExceptionHandler())
      .build();
  }

  // ========== PRIORITY 1: HAPPY PATH ==========

  @Test
  void login_ValidCredentials_Returns200WithTokens() throws Exception {
    LoginRequest request = new LoginRequest("testuser", "password123");
    User user = createTestUser();
    Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

    when(authenticationManager.authenticate(any())).thenReturn(auth);
    when(userService.getUserByUsername("testuser")).thenReturn(user);
    when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);
    when(jwtUtil.generateAccessToken(user)).thenReturn("access-token-123");
    when(jwtUtil.generateRefreshToken(user)).thenReturn("refresh-token-456");

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.accessToken").value("access-token-123"))
      .andExpect(jsonPath("$.refreshToken").value("refresh-token-456"))
      .andExpect(jsonPath("$.expiresIn").value(86400000L))
      .andExpect(jsonPath("$.user.username").value("testuser"));
  }

  // ========== PRIORITY 2: VALIDATION ERRORS ==========

  @Test
  void login_EmptyUsername_Returns400WithFieldError() throws Exception {
    LoginRequest request = new LoginRequest("", "password123");

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.VALIDATION_ERROR))
      .andExpect(jsonPath("$.fieldErrors.username").value("Username is required"));
  }

  @Test
  void login_EmptyPassword_Returns400WithFieldError() throws Exception {
    LoginRequest request = new LoginRequest("testuser", "");

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.VALIDATION_ERROR))
      .andExpect(jsonPath("$.fieldErrors.password").value("Password is required"));
  }

  @Test
  void login_BothFieldsEmpty_ReturnsBothFieldErrors() throws Exception {
    LoginRequest request = new LoginRequest("", "");

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.fieldErrors.username").exists())
      .andExpect(jsonPath("$.fieldErrors.password").exists());
  }

  @Test
  void login_WhitespaceOnlyUsername_ReturnsValidationError() throws Exception {
    LoginRequest request = new LoginRequest("   ", "password123");

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.fieldErrors.username").value("Username is required"));
  }

  // ========== PRIORITY 3: AUTHENTICATION FAILURES ==========

  @Test
  void login_WrongPassword_Returns401WithErrorCode() throws Exception {
    LoginRequest request = new LoginRequest("testuser", "wrongpassword");

    when(authenticationManager.authenticate(any()))
      .thenThrow(new BadCredentialsException("Bad credentials"));

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.INVALID_CREDENTIALS));
  }

  @Test
  void login_NonexistentUsername_Returns401WithErrorCode() throws Exception {
    LoginRequest request = new LoginRequest("nonexistent", "password123");

    when(authenticationManager.authenticate(any()))
      .thenThrow(new UsernameNotFoundException("User not found"));

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.INVALID_CREDENTIALS));
  }

  @Test
  void login_LockedAccount_Returns401WithErrorCode() throws Exception {
    LoginRequest request = new LoginRequest("lockeduser", "password123");

    when(authenticationManager.authenticate(any()))
      .thenThrow(new LockedException("Account locked"));

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.ACCOUNT_LOCKED));
  }

  @Test
  void login_DisabledAccount_Returns401WithErrorCode() throws Exception {
    LoginRequest request = new LoginRequest("disableduser", "password123");

    when(authenticationManager.authenticate(any()))
      .thenThrow(new DisabledException("Account disabled"));

    mockMvc.perform(post("/api/auth/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.errorCode").value(ErrorCodes.ACCOUNT_DISABLED));
  }

  private User createTestUser() {
    return User.builder()
      .id(1L)
      .username("testuser")
      .email("test@example.com")
      .firstName("Test")
      .lastName("User")
      .password("encoded-password")
      .enabled(true)
      .accountNonExpired(true)
      .accountNonLocked(true)
      .credentialsNonExpired(true)
      .build();
  }
}