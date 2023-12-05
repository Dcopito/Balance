package copito.security.infrastructure.security;

import copito.security.domain.repository.AccountRepositoryPort;
import copito.security.domain.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomAccountDetailsService {
    private AccountRepositoryPort repository;

    @Bean
    public UserDetailsService userDetailsService() throws RuntimeException{
        return email -> Optional.of(
                repository.findByEmail(email).get()
        ).map(account -> MainAccount.builder()
                .account(account)
                .id(account.getId())
                .user(account.getEmail())
                .password(account.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthrority(account.getRole().name())))
                .build())
                .get();
    }
}
