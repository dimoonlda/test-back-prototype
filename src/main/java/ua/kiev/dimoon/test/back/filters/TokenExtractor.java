package ua.kiev.dimoon.test.back.filters;

import java.util.Optional;

public class TokenExtractor {

    public Optional<String> extractToken(TokenExtractStrategy strategy) {
        return strategy.extract();
    }
}
