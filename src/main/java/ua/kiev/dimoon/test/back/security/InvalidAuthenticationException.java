package ua.kiev.dimoon.test.back.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {

    public InvalidAuthenticationException(String msg) {
        super(msg);
    }

    public InvalidAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
