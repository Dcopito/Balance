package copito.security.infrastructure.security.jwt;

import copito.security.domain.service.JwtProvider;
import copito.security.domain.utils.SecurityUtils;
import copito.security.infrastructure.security.MainAccount;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader)){
            filterChain.doFilter(request, response);
            return;
        }

        final String token = SecurityUtils.cleanBearer(authorizationHeader);
        final String email = jwtProvider.extractSubject(token);

        MainAccount account = (email != null)
                ? (MainAccount) this.userDetailsService.loadUserByUsername(email)
                : MainAccount.builder().build();
        if (jwtProvider.isTokenValid(token, account) &&
                SecurityContextHolder.getContext().getAuthentication() == null){
            var authUser = new UsernamePasswordAuthenticationToken(
                    account,
                    account.getPassword(),
                    account.getAuthorities()
            );

            authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authUser);
        }

        filterChain.doFilter(request, response);
    }
}
