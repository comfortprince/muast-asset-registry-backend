package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Boolean mustChangePassword;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
