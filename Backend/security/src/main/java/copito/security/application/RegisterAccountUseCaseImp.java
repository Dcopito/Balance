package copito.security.application;

import copito.security.domain.enums.Role;
import copito.security.domain.events.EventService;
import copito.security.domain.model.dto.RegisterUser;
import copito.security.domain.model.entities.Account;
import copito.security.domain.model.events.AccountCreatedDTO;
import copito.security.domain.repository.AccountRepositoryPort;
import copito.security.domain.usecase.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterAccountUseCaseImp implements RegisterAccountUseCase {
    private final AccountRepositoryPort repository;
    private final EventService eventService;
    @Override
    public Account register(RegisterUser registerUser) {
        String email = registerUser.getEmail();
        String password = registerUser.getPassword();
        String name = registerUser.getName();
        String lastName = registerUser.getLastName();

        var savedAccount = repository.registerAccount(email, password, Role.USER);

        eventService.execute(AccountCreatedDTO.builder()
                .id(savedAccount.getId())
                .role("USER")
                .name(name)
                .lastName(lastName)
                .build()
        );
        return savedAccount;
    }
}
