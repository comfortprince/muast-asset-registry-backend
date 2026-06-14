package ac.muast.it.asset_register.exception;

/**
 * Centralized error codes for frontend consumption.
 * These codes remain stable even if the human-readable messages change.
 */
public final class ErrorCodes {
    
    private ErrorCodes() {}
    
    // ===== AUTHENTICATION & AUTHORIZATION =====
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ACCOUNT_LOCKED = "ACCOUNT_LOCKED";
    public static final String ACCOUNT_DISABLED = "ACCOUNT_DISABLED";
    
    // ===== TOKEN-RELATED =====
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    
    // ===== RESOURCE CONFLICTS =====
    public static final String EMAIL_TAKEN = "EMAIL_TAKEN";
    public static final String USERNAME_TAKEN = "USERNAME_TAKEN";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    
    // ===== NOT FOUND =====
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String EMPLOYEE_NOT_FOUND = "EMPLOYEE_NOT_FOUND";
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    
    // ===== VALIDATION =====
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    
    // ===== GENERAL =====
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String BAD_REQUEST = "BAD_REQUEST";

    public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION";
}
