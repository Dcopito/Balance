package copito.security.infrastructure.controller;

import copito.security.domain.model.dto.LoginDTO;
import copito.security.domain.model.dto.LoginResponse;
import copito.security.domain.usecase.LoginUseCase;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase useCase;

    @Retry(name = "securityRetry")
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginDTO loginDTO){
        var response = useCase.login(loginDTO.getEmail(), loginDTO.getPassword());

        return ResponseEntity.ok(response);
    }
}
