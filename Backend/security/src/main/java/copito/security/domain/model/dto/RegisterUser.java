package copito.security.domain.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterUser {
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    @Length(max = 128, min = 5, message = "5 minimun and 128 maximun characters")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Length(max = 128, min = 8, message = "8 minimun and 128 maximun characters")
    private String password;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

}
