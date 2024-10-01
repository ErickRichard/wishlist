package br.com.personal.wishlist.configuration.security;

import br.com.personal.wishlist.application.port.JWTServicePort;
import br.com.personal.wishlist.domain.common.enumeration.MessageKey;
import br.com.personal.wishlist.domain.common.exception.CoreRuleException;
import br.com.personal.wishlist.domain.common.utils.MessageResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    JWTServicePort jwtServicePort;
    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        try {
            if (request.getRequestURI().contains("/auth/v1/") || containsSwaggerPath(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }

            if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
                throw new CoreRuleException(MessageResponse.error(MessageKey.TOKEN_REQUIRED));
            }


            final String jwt = authHeader.substring(7);
            final String userEmail = jwtServicePort.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (Objects.nonNull(userEmail) && Objects.isNull(authentication)) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (Boolean.TRUE.equals(jwtServicePort.validateToken(jwt, userDetails))) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private Boolean containsSwaggerPath(String requestURI) {
        return requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs/") ||
                requestURI.startsWith("/swagger-resources/") ||
                requestURI.startsWith("/webjars/") ||
                requestURI.equals("/swagger-ui/index.html");
    }
}
