package ac.muast.it.asset_register.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
  private String accessToken;
  
  @Builder.Default
  private String tokenType = "Bearer";
  
  private Long expiresIn;
  private UserInfo user;
  
  @Data
  @Builder
  public static class UserInfo {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
  }
}