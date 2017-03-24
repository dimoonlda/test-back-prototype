package ua.kiev.dimoon.test.back.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.kiev.dimoon.test.back.dao.interfaces.UserDAO;
import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

public class UserServiceImplTest {
    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Mockito.doNothing().when(userDao).create(any(User.class));
        UserDto userDto = createUserDto(null, TEST_USER_NAME);
        userService.create(userDto);
        Mockito.verify(userDao).create(any(User.class));
    }

    @Test
    public void testUpdate() {
        Mockito.doNothing().when(userDao).update(any(User.class));
        UserDto userDto = createUserDto(1L, TEST_USER_NAME);
        userService.update(userDto);
        Mockito.verify(userDao).update(any(User.class));
    }

    @Test
    public void testFindAll() {
        User user1 = createUser(1L, TEST_USER_NAME);
        User user2 = createUser(2L, TEST_USER_NAME);
        List<User> usersFromDao = Arrays.asList(user1, user2);
        when(userDao.findAll()).thenReturn(usersFromDao);
        List<UserDto> usersDtoResult = userService.findAll();
        assertThat(usersDtoResult.size()).isEqualTo(usersFromDao.size());
        Mockito.verify(userDao).findAll();
    }

    @Test
    public void testRemove() {
        Mockito.doNothing().when(userDao).remove(1L);
        userService.remove(1L);
        Mockito.verify(userDao).remove(1L);
    }

    @Test
    public void testFindUserByUsername() {
        User user = createUser(1L, TEST_USER_NAME);
        when(userDao.findUserByUsername(TEST_USER_NAME)).thenReturn(Optional.of(user));
        Optional<User> usersDtoResult = userService.findUserByUsername(TEST_USER_NAME);
        assertThat(user.getId()).isEqualTo(usersDtoResult.map(User::getId).orElse(null));
        Mockito.verify(userDao).findUserByUsername(TEST_USER_NAME);
    }

}
