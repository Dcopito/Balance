package copito.security.infrastructure.persistence.entities;

import copito.security.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity implements Serializable {
    private static final long SERIALVERSIONUUID = 1L;

    @Id
    private String id;
    @Column(name = "email", nullable = false, length = 128, unique = true)
    @Email(message = "Please type a valid email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "createdAt")
    private LocalDate createdAt;
    @Column(name = "lastSession")
    private LocalDate lastSession;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "role", nullable = false)
    private Role role;
    @Transient
    private String token;

    public AccountEntity(String email, String password, Role role){
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.createdAt = LocalDate.now();
        this.lastSession = LocalDate.now();
        this.active = true;
        this.role = role;
    }

}
