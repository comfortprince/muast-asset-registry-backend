package ac.muast.it.asset_registry.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_registry.dto.request.LoginRequest;
import ac.muast.it.asset_registry.dto.response.AuthResponse;
import ac.muast.it.asset_registry.exception.ErrorCodes;
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.repository.UserRepository;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class AuthControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    // Create a test user in database
    User user = User.builder()
      .username("testuser")
      .password(passwordEncoder.encode("password123"))
      .email("test@example.com")
      .firstName("Test")
      .lastName("User")
      .enabled(true)
      .accountNonExpired(true)
      .accountNonLocked(true)
      .credentialsNonExpired(true)
      .build();
    userRepository.save(user);
  }

  @Test
  void login_Success_RealDatabase() {
    // Arrange
    LoginRequest loginRequest = new LoginRequest("testuser", "password123");

    // Act
    ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
        "/api/auth/login",
        loginRequest,
        AuthResponse.class
    );

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getAccessToken()).isNotBlank();
    assertThat(response.getBody().getRefreshToken()).isNotBlank();
    assertThat(response.getBody().getExpiresIn()).isPositive();
    assertThat(response.getBody().getUser().getUsername()).isEqualTo("testuser");
    assertThat(response.getBody().getUser().getEmail()).isEqualTo("test@example.com");
    assertThat(response.getBody().getUser().getFirstName()).isEqualTo("Test");
    assertThat(response.getBody().getUser().getLastName()).isEqualTo("User");
    }

    // @Test
    // void login_Failure_WrongPassword_ShouldReturnUnauthorizedWithErrorCode() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    //     assertThat(response.getBody()).isNotNull();
        
    //     // Verify ProblemDetail structure
    //     assertThat(response.getBody()).containsKey("title");
    //     assertThat(response.getBody()).containsKey("detail");
    //     assertThat(response.getBody()).containsKey("status");
    //     assertThat(response.getBody()).containsKey("errorCode");
        
    //     // Verify specific values
    //     assertThat(response.getBody().get("status")).isEqualTo(401);
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.INVALID_CREDENTIALS);
    // }

    // @Test
    // void login_Failure_UserNotFound_ShouldReturnUnauthorizedWithErrorCode() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("nonexistentuser", "password123");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.INVALID_CREDENTIALS);
    // }

    // @Test
    // void login_ValidationFailure_EmptyUsername_ShouldReturnBadRequestWithFieldErrors() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("", "password123");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    //     assertThat(response.getBody()).isNotNull();
        
    //     // Verify ProblemDetail structure for validation errors
    //     assertThat(response.getBody().get("title")).isEqualTo("Validation Error");
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.VALIDATION_ERROR);
    //     assertThat(response.getBody().get("detail")).isEqualTo("Validation failed. Please check the submitted data.");
        
    //     // Verify field errors
    //     assertThat(response.getBody()).containsKey("fieldErrors");
    //     Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fieldErrors");
    //     assertThat(fieldErrors).containsKey("username");
    //     assertThat(fieldErrors.get("username")).isEqualTo("Username is required");
    //     assertThat(fieldErrors).doesNotContainKey("password"); // Password field is valid
    // }

    // @Test
    // void login_ValidationFailure_EmptyPassword_ShouldReturnBadRequestWithFieldErrors() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("testuser", "");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    //     assertThat(response.getBody()).isNotNull();
        
    //     // Verify error code
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.VALIDATION_ERROR);
        
    //     // Verify field errors
    //     Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fieldErrors");
    //     assertThat(fieldErrors).containsKey("password");
    //     assertThat(fieldErrors.get("password")).isEqualTo("Password is required");
    // }

    // @Test
    // void login_ValidationFailure_BothFieldsEmpty_ShouldReturnBothFieldErrors() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("", "");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.VALIDATION_ERROR);
        
    //     // Verify both field errors are present
    //     Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fieldErrors");
    //     assertThat(fieldErrors).hasSize(2);
    //     assertThat(fieldErrors.get("username")).isEqualTo("Username is required");
    //     assertThat(fieldErrors.get("password")).isEqualTo("Password is required");
    // }

    // @Test
    // void login_ValidationFailure_WhitespaceOnly_ShouldReturnValidationError() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("   ", "   ");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().get("errorCode")).isEqualTo(ErrorCodes.VALIDATION_ERROR);
        
    //     Map<String, String> fieldErrors = (Map<String, String>) response.getBody().get("fieldErrors");
    //     assertThat(fieldErrors).hasSize(2);
    // }

    // @Test
    // void login_AuthenticationFailure_AccountLocked_ShouldReturnLockedError() {
    //     // Arrange - Create a locked user
    //     User lockedUser = User.builder()
    //         .username("lockeduser")
    //         .password(passwordEncoder.encode("password123"))
    //         .email("locked@example.com")
    //         .firstName("Locked")
    //         .lastName("User")
    //         .enabled(true)
    //         .accountNonLocked(false) // Account is locked
    //         .accountNonExpired(true)
    //         .credentialsNonExpired(true)
    //         .build();
    //     userRepository.save(lockedUser);
        
    //     LoginRequest loginRequest = new LoginRequest("lockeduser", "password123");

    //     // Act
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         Map.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    //     assertThat(response.getBody()).isNotNull();
    //     // Should return appropriate error code for locked account
    //     // Note: This depends on how your AuthenticationManager handles locked accounts
    // }

    // @Test
    // void login_Success_ShouldUpdateLastLoginTimestamp() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("testuser", "password123");
        
    //     // Get original user
    //     User originalUser = userRepository.findByUsername("testuser").orElseThrow();
    //     assertThat(originalUser.getLastLogin()).isNull(); // Initially null
        
    //     // Act
    //     ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         AuthResponse.class
    //     );
        
    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
    //     // Verify last login was updated
    //     User updatedUser = userRepository.findByUsername("testuser").orElseThrow();
    //     assertThat(updatedUser.getLastLogin()).isNotNull();
    // }

    // @Test
    // void login_ResponseStructure_ShouldHaveCorrectTokenFormat() {
    //     // Arrange
    //     LoginRequest loginRequest = new LoginRequest("testuser", "password123");

    //     // Act
    //     ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
    //         "/api/auth/login",
    //         loginRequest,
    //         AuthResponse.class
    //     );

    //     // Assert
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     AuthResponse authResponse = response.getBody();
        
    //     // Verify JWT token format (should be 3 parts separated by dots)
    //     assertThat(authResponse.getAccessToken()).matches("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$");
    //     assertThat(authResponse.getRefreshToken()).matches("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$");
        
    //     // Verify expiration is reasonable (should be 24 hours = 86400000 ms)
    //     assertThat(authResponse.getExpiresIn()).isEqualTo(86400000L);
    // }
}