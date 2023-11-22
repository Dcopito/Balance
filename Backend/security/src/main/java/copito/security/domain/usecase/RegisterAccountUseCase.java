package copito.security.domain.usecase;

import copito.security.domain.model.dto.RegisterUser;
import copito.security.domain.model.entities.Account;

public interface RegisterAccountUseCase {
    Account register(RegisterUser registerUser);
}
