package copito.security.infrastructure.security.jwt;

import copito.security.domain.service.JwtProvider;
import copito.security.infrastructure.security.MainAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtProviderImp implements JwtProvider<MainAccount> {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    @Value("${app.jwt.expiration-in-minute}")
    private String JWT_EXPIRATION;


    @Override
    public String generateToken(MainAccount userDetails) {
        return this.generateToken(userDetails, new HashMap<>());
    }

    @Override
    public Boolean hasClaim(String token, String name) {
        return this.extractAllClaims(token).get(name) != null;
    }

    @Override
    public String extractSubject(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpired(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public Boolean isTokenValid(String token, MainAccount userDetails) {
        Assert.isTrue(StringUtils.hasText(token), "The token is empty, token is invalid");
        final String accountEmail = this.extractSubject(token);
        Assert.notNull(userDetails, "The user details is null, token is invalid");
        Assert.isTrue(StringUtils.hasText(accountEmail), "the token is empty token is invalid");
        Assert.isTrue(accountEmail.equals(userDetails.getUsername()), "Email does not match, token is invalid");

        return !istokenExpired(token);
    }

    @Override
    public Boolean istokenExpired(String token) {
       return this.extractExpired(token).before(new Date());
    }

    @Override
    public String generateToken(MainAccount userDetails, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("id", userDetails.getId())
                .claim("authority", userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(JWT_EXPIRATION))))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public <S> S extractClaim(String token, String name, Class<S> type) {
        if (!this.hasClaim(token, name)) return null;
        var claims = this.extractAllClaims(token);

        return type.cast(claims.get(name));
    }

    public <T> T extractClaim(String token, Function<Claims, T > resolver){
        final Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


}
