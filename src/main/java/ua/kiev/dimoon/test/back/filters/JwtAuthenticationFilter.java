package ua.kiev.dimoon.test.back.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.kiev.dimoon.test.back.security.InvalidAuthenticationException;
import ua.kiev.dimoon.test.back.security.JwtAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        TokenExtractor tokenExtractor = new TokenExtractor();
        Optional<String> authToken = tokenExtractor.extractToken(new TokenExtractFromHeaderStrategy(request));
        if (!authToken.isPresent()) {
            authToken = tokenExtractor.extractToken(new TokenExtractFromUrlParamStrategy(request));
        }
        if (!authToken.isPresent()) {
            authToken = tokenExtractor.extractToken(new TokenExtractFromCookieStrategy(request));
        }
        if (!authToken.isPresent()) {
            chain.doFilter(request, response);
            return;
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(authToken.get()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }catch (InvalidAuthenticationException e) {
            LOG.error("Authentication with token exception.", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
