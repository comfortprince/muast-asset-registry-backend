package ac.muast.it.asset_register.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Auditable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private String firstName;

    private String lastName;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    
    @Builder.Default
    private Boolean enabled = true;
    
    @Builder.Default
    private Boolean accountNonExpired = true;
    
    @Builder.Default
    private Boolean accountNonLocked = true;
    
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    
    private String resetToken;
    
    private LocalDateTime resetTokenExpiry;

    @Builder.Default
    private Boolean mustChangePassword = false;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return Set.of();
        return roles.stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.name()))
            .collect(Collectors.toSet());
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired != null && accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked != null && accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired != null && credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled != null && enabled;
    }

    public Boolean isMustChangePassword() {
        return mustChangePassword;
    }
}