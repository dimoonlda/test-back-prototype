package ua.kiev.dimoon.test.back.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
 * Created by admin on 22.03.2017.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String name;
    private String token;

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String name, String token) {
        super(authorities);
        this.token = token;
        this.name = name;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
