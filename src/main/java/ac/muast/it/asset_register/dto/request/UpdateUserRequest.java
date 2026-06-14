package ac.muast.it.asset_register.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;


@Data
public class UpdateUserRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    
    private String password;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    private Boolean enabled;
    private Set<String> roles;
}