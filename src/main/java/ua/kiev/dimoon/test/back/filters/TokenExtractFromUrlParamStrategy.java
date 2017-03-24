package ua.kiev.dimoon.test.back.filters;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by admin on 24.03.2017.
 */
public class TokenExtractFromUrlParamStrategy implements TokenExtractStrategy{
    public static final String TOKEN_URL_PARAM_NAME = "token";

    private HttpServletRequest request;

    public TokenExtractFromUrlParamStrategy(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<String> extract() {
        String authToken = request.getParameter(TOKEN_URL_PARAM_NAME);
        return Optional.ofNullable(authToken);
    }
}
