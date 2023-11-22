package copito.security.domain.usecase;

import copito.security.domain.model.dto.LoginResponse;

public interface GetCurrentSessionUseCase {
    LoginResponse getCurrentSession(String email);
}
