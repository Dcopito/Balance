package copito.security.infrastructure.controller;

import copito.security.domain.model.dto.LoginResponse;
import copito.security.domain.usecase.GetCurrentSessionUseCase;
import copito.security.infrastructure.security.MainAccount;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/current-session")
@RequiredArgsConstructor
public class GetCurrentSessionController {
    private final GetCurrentSessionUseCase currentSession;

    @Retry(name = "securityRetry")
    @GetMapping
    public ResponseEntity<LoginResponse> getCurrentSession(@AuthenticationPrincipal MainAccount mainAccount){
        var session = mainAccount.getUsername();

        return ResponseEntity.ok(currentSession.getCurrentSession(session));
    }
}
