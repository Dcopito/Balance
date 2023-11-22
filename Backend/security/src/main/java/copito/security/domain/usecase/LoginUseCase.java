package copito.security.domain.usecase;

import copito.security.domain.model.dto.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(final String email, final String password);
}
