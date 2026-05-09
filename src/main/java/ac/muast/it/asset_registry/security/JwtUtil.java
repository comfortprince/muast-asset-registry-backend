package ac.muast.it.asset_registry.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ac.muast.it.asset_registry.model.User;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;
    
    // Why? - Generate signing key from secret
    // Convert base64Key to SecretKey key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }
    
    // // Why? - Extract username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Why? - Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    // Why? - Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    
    // Why? - Generate access token after successful login
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", user.getAuthorities());
        claims.put("type", "access");
        return createToken(claims, user.getUsername(), expiration);
    }
    
    // Why? - Generate refresh token (longer lived, can get new access token)
    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return createToken(claims, user.getUsername(), refreshExpiration);
    }
    
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
            .header()
                .and()
            .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey())
            .compact();        
    }
    
    // Why? - Validate token during each request
    public Boolean validateToken(String token, User user) {
        if (token == null || user == null) {
            return false;
        }
        
        try {
            Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            
            // Multiple validation checks
            boolean isUsernameValid = username != null && username.equals(user.getUsername());
            boolean isNotExpired = expiration != null && expiration.after(new Date());
            boolean isUserEnabled = user.isEnabled();
            boolean isAccountNonLocked = user.isAccountNonLocked();
            
            return isUsernameValid && isNotExpired && isUserEnabled && isAccountNonLocked;
            
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception for monitoring
            // log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }
    
    // Why? - Check if token is refresh token
    public Boolean isRefreshToken(String token) {
        final String type = extractClaim(token, claims -> claims.get("type", String.class));
        return "refresh".equals(type);
    }
}