package copito.security.infrastructure.security;

import copito.security.domain.repository.AccountRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CustomAccountDetailsService {
    @Autowired
    private AccountRepositoryPort repository;

    public UserDetailsService userDetailsService() throws RuntimeException{
        return email -> Optional.of(
                repository.findByEmail(email).get()
        ).map(account -> MainAccount.builder()
                .account(account)
                .id(account.getId())
                .user(account.getEmail())
                .password(account.getPassword())
                .authorities(Set.of(SecurityUtil.conver))
                .build())
    }
}
