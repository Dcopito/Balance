package copito.security.infrastructure.security;

import copito.security.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringAuthService implements AuthService {
    private final AuthenticationManager authenticationManager;

    @Override
    public MainAccount authenticate(String email, String credential) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, credential));

        return (MainAccount) authentication.getPrincipal();
    }
}
