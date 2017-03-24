package ua.kiev.dimoon.test.back.services.interfaces;

import java.util.Optional;

/**
 * Created by admin on 22.03.2017.
 */
public interface TokenService {
    String TOKEN_FIELD_USER_NAME = "username";
    String TOKEN_FIELD_CREATE_DATE = "token_create_date";
    String TOKEN_FIELD_EXPIRATION_DATE = "token_expiration_date";

    Optional<String> createToken(String userName, String passWord);
    Boolean checkToken(String token);
}
