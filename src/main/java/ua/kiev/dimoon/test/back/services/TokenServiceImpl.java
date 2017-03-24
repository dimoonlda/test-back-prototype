package ua.kiev.dimoon.test.back.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class TokenServiceImpl implements TokenService {
    Logger LOG = LoggerFactory.getLogger(TokenServiceImpl.class);

    private UserService userService;

    @Autowired
    public TokenServiceImpl setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    public Optional<String> createToken(String userName, String passWord){
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return Optional.empty();
        }
        return userService.findUserByUsername(userName).map(user -> {
            String token = null;
            if (passWord.equals(user.getPassWord())) {
                Map<String, Object> tokenData = new HashMap<>();
                tokenData.put(TOKEN_FIELD_USER_NAME, user.getUserName());
                tokenData.put(TOKEN_FIELD_CREATE_DATE, ZonedDateTime.now().toEpochSecond());
                tokenData.put(TOKEN_FIELD_EXPIRATION_DATE, ZonedDateTime.now().plusMinutes(10L).toEpochSecond());
                token = TokenUtils.generateToken(tokenData);
            }
            return token;
        });
    }

    @Override
    public Boolean checkToken(String token) {
        Boolean isValid = false;
        try {
            Jws<Claims> claimsJws = TokenUtils.parseToken(token);
            Long now = ZonedDateTime.now().toEpochSecond();
            Integer tokenExpirationDate = claimsJws.getBody().get(TOKEN_FIELD_EXPIRATION_DATE, Integer.class);
            if (now <= tokenExpirationDate) {
                isValid = true;
            }
        }catch (Exception e) {
            LOG.error("Check token exception.", e);
        }
        return isValid;
    }
}
