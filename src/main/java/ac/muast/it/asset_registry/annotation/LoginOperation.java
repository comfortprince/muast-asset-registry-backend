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

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
    summary = "Authenticate user and get JWT tokens",
    description = """
      Authenticates a user with username and password. Returns JWT access and refresh tokens.
      
      **Authentication Scenarios:**
      - **Valid credentials**: Returns 200 with tokens
      - **Invalid password**: Returns 401 with INVALID_CREDENTIALS
      - **Non-existent user**: Returns 401 with INVALID_CREDENTIALS
      - **Locked account**: Returns 401 with ACCOUNT_LOCKED
      - **Disabled account**: Returns 401 with ACCOUNT_DISABLED
      - **Validation errors**: Returns 400 with VALIDATION_ERROR and field-specific messages
      
      **Token Information:**
      - Access token expires in 24 hours (86400000ms)
      - Refresh token can be used to obtain new access tokens
      - Both tokens are JWT format (header.payload.signature)
      """
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Successfully authenticated",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = AuthResponse.class),
        examples = @ExampleObject(
          name = "Successful Login",
          value = """
            {
              "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTY5OTk5OTk5OX0.signature",
              "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTY5OTk5OTk5OX0.signature",
              "expiresIn": 86400000,
              "user": {
                "id": 1,
                "username": "testuser",
                "email": "user@example.com",
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
      description = "Validation failed - Invalid request body",
      content = @Content(
        mediaType = "application/problem+json",
        schema = @Schema(implementation = ErrorResponse.class),
        examples = {
          @ExampleObject(
            name = "Empty Username",
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
            name = "Empty Password",
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
            name = "Both Fields Empty",
            value = """
              {
                "title": "Validation Error",
                "detail": "Validation failed. Please check the submitted data.",
                "status": 400,
                "errorCode": "VALIDATION_ERROR",
                "fieldErrors": {
                  "username": "Username is required",
                  "password": "Password is required"
                }
              }
              """
          )
        }
      )
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Authentication failed",
      content = @Content(
        mediaType = "application/problem+json",
        schema = @Schema(implementation = ErrorResponse.class),
        examples = {
          @ExampleObject(
            name = "Invalid Credentials",
            value = """
              {
                "title": "Unauthorized",
                "detail": "Bad credentials",
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
                "detail": "User account is locked",
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
                "detail": "User account is disabled",
                "status": 401,
                "errorCode": "ACCOUNT_DISABLED"
              }
              """
          )
        }
      )
    )
  })
public @interface LoginOperation {}
