package copito.apigateway.jwt;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtProvider {
    Boolean hasClaim(String token, String name);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    String cleanBearerToken(String authToken) throws Exception;
    Date extractExpired(String token);
    Claims extractAllClaims(String token);
    Boolean isTokenValid(String token);
    Boolean isTokenExpired(String token);

}
