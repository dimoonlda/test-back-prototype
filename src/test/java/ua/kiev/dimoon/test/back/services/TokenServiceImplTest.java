package ua.kiev.dimoon.test.back.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.services.interfaces.TokenService;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

/**
 * Created by admin on 23.03.2017.
 */
public class TokenServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private TokenService tokenService = new TokenServiceImpl();

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateToken() {
        User user = createUser(1L, TEST_USER_NAME);
        when(userService.findUserByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        String token = tokenService.createToken(TEST_USER_NAME, TEST_USER_PASS).orElse(null);
        assertThat(token).isNotEmpty();
        Mockito.verify(userService).findUserByUsername(TEST_USER_NAME);
    }

    @Test
    public void testCreateTokenWhenPasswordsDifferent() {
        User user = createUser(1L, TEST_USER_NAME);
        when(userService.findUserByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        String token = tokenService.createToken(TEST_USER_NAME, "Wrong pass").orElse(null);
        assertThat(token).isNullOrEmpty();
        Mockito.verify(userService).findUserByUsername(TEST_USER_NAME);
    }

    @Test
    public void testCheckToken() {
        User user = createUser(1L, TEST_USER_NAME);
        when(userService.findUserByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        String token = tokenService.createToken(TEST_USER_NAME, TEST_USER_PASS).orElse(null);
        assertThat(token).isNotEmpty();
        assertThat(tokenService.checkToken(token)).isTrue();
    }

    @Test
    public void testCheckTokenWithWrongToken() {
        assertThat(tokenService.checkToken("Wrong token")).isFalse();
    }

}
