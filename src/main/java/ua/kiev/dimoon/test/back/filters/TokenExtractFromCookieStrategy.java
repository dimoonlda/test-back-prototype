package ua.kiev.dimoon.test.back.filters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by admin on 24.03.2017.
 */
public class TokenExtractFromCookieStrategy implements TokenExtractStrategy{
    public static final String TOKEN_COOKIE_NAME = "Token";

    private HttpServletRequest request;

    public TokenExtractFromCookieStrategy(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Optional<String> extract() {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue);
    }
}
