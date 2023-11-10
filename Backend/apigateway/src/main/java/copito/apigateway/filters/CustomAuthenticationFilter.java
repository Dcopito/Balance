package copito.apigateway.filters;

import copito.apigateway.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpHeaders;
import java.util.function.Consumer;

@Slf4j
@Component
public class CustomAuthenticationFilter extends AbstractGatewayFilterFactory<CustomAuthenticationFilter.Config> {
    private final JwtProvider jwtProvider;

    @Autowired
    public CustomAuthenticationFilter(JwtProvider jwtProvider){
        super(Config.class);

        this.jwtProvider = jwtProvider;
    }

    @Override
    public GatewayFilter apply(CustomAuthenticationFilter.Config config) {
        log.info("Gateway filter, before return" );

        return (exchange, chain) -> {
            log.info("..");
            log.info("Route: {}", exchange.getRequest().getPath());
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            try {
                String token = jwtProvider.cleanBearerToken(authHeader);
                jwtProvider.extractAllClaims(token);

                if (jwtProvider.isTokenExpired(token)){
                        throw new Exception("The token is already expired");
                }

                if (!jwtProvider.hasClaim(token, "id") || !jwtProvider.hasClaim(token, "authority")){
                        throw new Exception("The token structure is invalid");
                }
            }catch (Exception e){
                log.error("INVALID_TOKEN");
                log.error("Exception: {}", e.getMessage());
            }

        }
    }


    public static class Config{
    }
}
