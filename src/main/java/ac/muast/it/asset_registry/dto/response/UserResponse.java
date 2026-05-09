package ac.muast.it.asset_registry.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
    private boolean enabled;
    private boolean mustChangePassword;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}