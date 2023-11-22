package copito.security.domain.model.dto;

import copito.security.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private String email;
    private String token;
    private String id;
    private Role role;
}
