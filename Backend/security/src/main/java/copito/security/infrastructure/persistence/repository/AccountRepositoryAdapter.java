package copito.security.infrastructure.persistence.repository;

import copito.security.domain.enums.Role;
import copito.security.domain.exceptions.AccountAlreadyExists;
import copito.security.domain.model.entities.Account;
import copito.security.domain.repository.AccountRepositoryPort;
import copito.security.infrastructure.persistence.entities.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;
    private final PasswordEncoder passwordEncoder;

    private final Function<AccountEntity, Account> entityToModel = (entity) -> new Account(
            entity.getId(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getCreatedAt(),
            entity.getLastSession(),
            entity.getRole()
    );

    @Override
    public Optional<Account> findByEmail(String email) {
        if(!StringUtils.hasText(email)){
            throw new IllegalArgumentException("Email cannot be empty");
        }
        var account = jpaRepository.findByEmail(email);

        return account.isEmpty() ? Optional.empty() : account.map(entityToModel::apply);
    }

    @Override
    public Account registerAccount(String email, String password, Role rol) {
        if (this.findByEmail(email).isPresent()){
            throw new AccountAlreadyExists("This account already exists");
        }

        var encryptPassword = passwordEncoder.encode(password);

        var account = jpaRepository.save(new AccountEntity(email, encryptPassword, rol));

        return entityToModel.apply(account);
    }
}
