package ac.muast.it.asset_register.exception;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ProblemDetail handleUserAlreadyExists(UserAlreadyExistsException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.CONFLICT, 
      ex.getMessage()
    );
    problemDetail.setProperty("errorCode", ErrorCodes.USER_ALREADY_EXISTS);
    log.error("User Already Exists", ex);
    return problemDetail;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.UNAUTHORIZED, 
      ex.getMessage()
    );
    problemDetail.setProperty("errorCode", ErrorCodes.INVALID_CREDENTIALS);
    log.error("Bad Credentials", ex); 
    return problemDetail;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleUserNotFound(ResourceNotFoundException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      ex.getMessage()
    );
    problemDetail.setTitle("Resource Not Found");
    problemDetail.setProperty("errorCode", ErrorCodes.USER_NOT_FOUND);
    log.error("Resource Not Found", ex);
    return problemDetail;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        
    Map<String, String> fieldErrors = new HashMap<>();
        
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      fieldErrors.put(fieldName, errorMessage);
    });

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      "Validation failed. Please check the submitted data."
    );

    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("errorCode", ErrorCodes.VALIDATION_ERROR);
    problemDetail.setProperty("fieldErrors", fieldErrors);
    
    log.error("Invalid Input", ex);

    return problemDetail;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(LockedException.class)
  public ProblemDetail handleLockedException(LockedException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.UNAUTHORIZED,
      ex.getMessage()
    );
    problemDetail.setTitle("Account Locked");
    problemDetail.setProperty("errorCode", ErrorCodes.ACCOUNT_LOCKED);
    log.error("Account Locked", ex);
    return problemDetail;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(DisabledException.class)
  public ProblemDetail handleDisabledException(DisabledException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.UNAUTHORIZED,
      ex.getMessage()
    );
    problemDetail.setTitle("Account Disabled");
    problemDetail.setProperty("errorCode", ErrorCodes.ACCOUNT_DISABLED);
    log.error("Account Disabled", ex);
    return problemDetail;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolation(
    jakarta.validation.ConstraintViolationException ex) {

    Map<String, String> fieldErrors = new HashMap<>();

    ex.getConstraintViolations().forEach(violation -> {
      String fieldName = violation.getPropertyPath().toString();
      // Extract just the field name from the path (e.g., "password" from "password")
      String simpleFieldName = fieldName.contains(".") 
        ? fieldName.substring(fieldName.lastIndexOf('.') + 1) 
        : fieldName;
      String message = violation.getMessage();
      fieldErrors.put(simpleFieldName, message);
    });

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      "Validation failed. Please check the submitted data."
    );

    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("errorCode", ErrorCodes.VALIDATION_ERROR);
    problemDetail.setProperty("fieldErrors", fieldErrors);

    log.error("Constraint violation: {}", fieldErrors, ex);

    return problemDetail;
  }
}