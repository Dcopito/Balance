package copito.security.domain.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginDTO {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be Empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
