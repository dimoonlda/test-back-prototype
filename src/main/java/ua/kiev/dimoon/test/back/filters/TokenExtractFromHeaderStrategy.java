package ua.kiev.dimoon.test.back.filters;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by admin on 24.03.2017.
 */
public class TokenExtractFromHeaderStrategy implements TokenExtractStrategy {
    public static final String TOKEN_NAME = "Token";

    private HttpServletRequest request;

    public TokenExtractFromHeaderStrategy(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<String> extract() {
        String authToken = request.getHeader(TOKEN_NAME);
        return Optional.ofNullable(authToken);
    }
}
