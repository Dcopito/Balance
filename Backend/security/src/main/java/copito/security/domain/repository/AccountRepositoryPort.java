package copito.security.domain.repository;

import copito.security.domain.enums.Role;
import copito.security.domain.model.entities.Account;

import java.util.Optional;

public interface AccountRepositoryPort {
    Optional<Account> findByEmail(final String email);
    Account registerAccount(final String email, final String password, final Role rol);
}
