package copito.security.infrastructure.security;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
        if(authentication.isAuthenticated()){
            return authentication;
        }

        try {
            String email = authentication.getPrincipal().toString();
            var accountAthenticated = this.service.loadUserByUsername(email);

            String password = authentication.getCredentials().toString();
            this.verifyPassword(password, accountAthenticated.getPassword());

            var auth = UsernamePasswordAuthenticationToken.authenticated(
                    accountAthenticated,
                    accountAthenticated.getPassword(),
                    accountAthenticated.getAuthorities()
            );

            auth.setDetails(authentication.getDetails());

            return auth;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("An error has ocurred while authenticating the user credentials");
        }
    }

    private void verifyEnable(Boolean enable){
        if (!enable){
            throw new RuntimeException("The user trying to access is disabled");
        }
    }

    private void verifyPassword(String rawPassword, String encodedPassword){
        if(!this.encoder.matches(rawPassword, encodedPassword))
            throw new RuntimeException("The password does not belong to the user");
    }
}
