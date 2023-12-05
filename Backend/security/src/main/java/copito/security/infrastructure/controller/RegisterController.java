package copito.security.infrastructure.controller;

import copito.security.domain.model.dto.RegisterUser;
import copito.security.domain.usecase.RegisterAccountUseCase;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/accounts/regsiter")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterAccountUseCase useCase;

    @Retry(name = "securityRetry")
    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody @Valid RegisterUser register){
        useCase.register(register);

        return ResponseEntity.ok().build();
    }
}
