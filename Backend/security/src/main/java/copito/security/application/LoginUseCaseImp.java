package copito.security.application;

import copito.security.domain.model.dto.LoginResponse;
import copito.security.domain.service.AuthService;
import copito.security.domain.service.JwtProvider;
import copito.security.domain.usecase.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCaseImp implements LoginUseCase {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    @Override
    public LoginResponse login(String email, String password) {
        var accountAuthenticated = authService.authenticate(email, password);

        var token = jwtProvider.generateToken(accountAuthenticated);

        var loginResponse = new LoginResponse();
        loginResponse.setEmail(email);
        loginResponse.setToken(token);
        loginResponse.setRole(accountAuthenticated.getAccount().getRole());
        loginResponse.setId(accountAuthenticated.getId());

        return loginResponse;
    }
}
