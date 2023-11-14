package copito.security.domain.service;

import copito.security.infrastructure.security.MainAccount;

import java.util.Date;
import java.util.Map;

public interface JwtProvider<T> {
    String generateToken(T userDetails);
    String generateToken(T userDetails, Map<String, Object> extraClaims);
    <S> S extractClaim(String token, String name, Class<S> type);
    String generateToken(MainAccount userDetails, Map<String, Object> extraClaims);
    Boolean hasClaim(String token, String name);
    String extractSubject(String token);
    Date extractExpired(String token);
    Boolean isTokenValid(String token, T userDetails);
    Boolean istokenExpired(String token);
}
