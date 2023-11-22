package copito.security.domain.usecase;

import copito.security.domain.model.entities.Account;

public interface FindEmailUseCase {
    Account findEmail(String email);
}
