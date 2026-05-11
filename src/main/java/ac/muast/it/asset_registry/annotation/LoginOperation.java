package ac.muast.it.asset_registry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ac.muast.it.asset_registry.dto.response.AuthResponse;
import ac.muast.it.asset_registry.dto.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = "Authenticate user and get JWT access token",
    description = """
      Authenticates a user using HTTP Basic Authentication (username/password). 
      Returns a JWT access token valid for 60 minutes.
      
      **How to use:**
      - Send request with `Authorization: Basic base64(username:password)` header
      - Or use Swagger UI's "Authorize" button with Basic Auth
      
      **Authentication Scenarios:**
      | Scenario | HTTP Status | Error Code |
      |----------|------------|-------------|
      | Valid credentials | 200 OK | - |
      | Invalid password | 401 UNAUTHORIZED | INVALID_CREDENTIALS |
      | Non-existent user | 401 UNAUTHORIZED | INVALID_CREDENTIALS |
      | Locked account | 401 UNAUTHORIZED | ACCOUNT_LOCKED |
      | Disabled account | 401 UNAUTHORIZED | ACCOUNT_DISABLED |
      | Missing credentials | 401 UNAUTHORIZED | MISSING_CREDENTIALS |
      
      **Token Information:**
      - Access token type: JWT (JSON Web Token)
      - Token lifetime: 60 minutes (3600 seconds)
      - Token format: `header.payload.signature`
      - Algorithm: RS256 or HS256 (configured in JwtService)
      
      **After Login:**
      1. Extract the `accessToken` from response
      2. Include in subsequent requests: `Authorization: Bearer <token>`
      3. User's `lastLogin` timestamp is automatically updated
      
      **Note:** This API does NOT issue refresh tokens. When the access token expires,
      the user must re-authenticate with username/password.
      """,
    security = @SecurityRequirement(name = "basicAuth")  // Indicates Basic Auth required
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Successfully authenticated - Returns JWT access token",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AuthResponse.class),
            examples = @ExampleObject(
                name = "Successful Login Response",
                value = """
                    {
                      "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqb2huLmRvZSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3MzQ5MTUyMDAsImV4cCI6MTczNDkxODgwMH0.signature",
                      "expiresIn": 60,
                      "user": {
                        "id": 1,
                        "username": "john.doe",
                        "email": "john.doe@example.com",
                        "firstName": "John",
                        "lastName": "Doe"
                      }
                    }
                    """
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Validation failed - Invalid request format",
        content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = {
                @ExampleObject(
                    name = "Missing Username",
                    value = """
                        {
                          "title": "Validation Error",
                          "detail": "Validation failed. Please check the submitted data.",
                          "status": 400,
                          "errorCode": "VALIDATION_ERROR",
                          "fieldErrors": {
                            "username": "Username is required"
                          }
                        }
                        """
                ),
                @ExampleObject(
                    name = "Missing Password",
                    value = """
                        {
                          "title": "Validation Error",
                          "detail": "Validation failed. Please check the submitted data.",
                          "status": 400,
                          "errorCode": "VALIDATION_ERROR",
                          "fieldErrors": {
                            "password": "Password is required"
                          }
                        }
                        """
                ),
                @ExampleObject(
                    name = "Invalid Authorization Header Format",
                    value = """
                        {
                          "title": "Bad Request",
                          "detail": "Invalid Authorization header format. Expected 'Basic base64(username:password)'",
                          "status": 400,
                          "errorCode": "INVALID_AUTH_HEADER"
                        }
                        """
                )
            }
        )
    ),
    @ApiResponse(
        responseCode = "401",
        description = "Authentication failed - Invalid credentials or account issue",
        content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = {
                @ExampleObject(
                    name = "Invalid Credentials",
                    value = """
                        {
                          "title": "Unauthorized",
                          "detail": "Invalid username or password",
                          "status": 401,
                          "errorCode": "INVALID_CREDENTIALS"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Account Locked",
                    value = """
                        {
                          "title": "Account Locked",
                          "detail": "User account is locked due to multiple failed attempts",
                          "status": 401,
                          "errorCode": "ACCOUNT_LOCKED"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Account Disabled",
                    value = """
                        {
                          "title": "Account Disabled",
                          "detail": "User account is disabled. Contact administrator.",
                          "status": 401,
                          "errorCode": "ACCOUNT_DISABLED"
                        }
                        """
                ),
                @ExampleObject(
                    name = "Missing Credentials",
                    value = """
                        {
                          "title": "Unauthorized",
                          "detail": "No credentials provided",
                          "status": 401,
                          "errorCode": "MISSING_CREDENTIALS"
                        }
                        """
                )
            }
        )
    ),
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(
            mediaType = "application/problem+json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                name = "Database Error",
                value = """
                    {
                      "title": "Internal Server Error",
                      "detail": "An unexpected error occurred while processing login",
                      "status": 500,
                      "errorCode": "INTERNAL_ERROR"
                    }
                    """
            )
        )
    )
})
public @interface LoginOperation {}