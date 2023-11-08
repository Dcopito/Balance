package copito.security.domain.service;

import copito.security.infrastructure.security.MainAccount;

public interface AuthService {
    MainAccount authenticate(String email, String credential);
}
