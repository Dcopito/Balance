package copito.security.domain.model.entities;

import copito.security.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Account {
    private String id;
    private String email;
    private String password;
    private LocalDate cratedAt;
    private LocalDate lastSession;
    private Role role;
}
