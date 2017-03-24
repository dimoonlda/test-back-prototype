package ua.kiev.dimoon.test.back.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.kiev.dimoon.test.back.services.TokenUtils;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    Logger LOG = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private TokenService tokenService;

    public JwtAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthenticationToken) {
            String token = ((JwtAuthenticationToken) authentication).getToken();
            if (!tokenService.checkToken(token)) {
                LOG.debug("Token is wrong: {}, ", token);
                throw new InvalidAuthenticationException("Wrong token");
            }
            try {
                return getJwtAuthentication(token);
            } catch (RuntimeException e) {
                throw new InvalidAuthenticationException("Access denied", e);
            }
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Authentication getJwtAuthentication(String token) {
        Jws<Claims> claimsJws = TokenUtils.parseToken(token);
        List<SimpleGrantedAuthority> grantedAuthorities = grantedAuthorities(Arrays.asList("USER"));
        return new JwtAuthenticationToken(
                grantedAuthorities,
                claimsJws.getBody().get(TokenService.TOKEN_FIELD_USER_NAME, String.class),
                token);
    }

    private List<SimpleGrantedAuthority> grantedAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}
