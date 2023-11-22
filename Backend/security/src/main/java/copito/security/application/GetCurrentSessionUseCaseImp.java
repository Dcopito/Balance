package copito.security.application;

import copito.security.domain.enums.Role;
import copito.security.domain.model.dto.LoginResponse;
import copito.security.domain.service.JwtProvider;
import copito.security.domain.usecase.GetCurrentSessionUseCase;
import copito.security.infrastructure.security.MainAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCurrentSessionUseCaseImp implements GetCurrentSessionUseCase {
    private final UserDetailsService service;
    private final JwtProvider jwtProvider;


    @Override
    public LoginResponse getCurrentSession(String email) {

        var account = ((MainAccount)service.loadUserByUsername(email));

        String token = jwtProvider.generateToken(account);

        return LoginResponse.builder()
                .email(email)
                .token(token)
                .role(Role.valueOf(account.getAuthorities().stream().findFirst().get().getAuthority().substring(5)))
                .id(account.getId())
                .build();
    }
}
