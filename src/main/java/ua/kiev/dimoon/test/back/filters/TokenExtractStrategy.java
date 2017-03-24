package ua.kiev.dimoon.test.back.filters;

import java.util.Optional;

public interface TokenExtractStrategy {
    Optional<String> extract();
}
