package ua.kiev.dimoon.test.back.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dimoon.test.back.BaseTestClass;
import ua.kiev.dimoon.test.back.dao.interfaces.RoleDAO;
import ua.kiev.dimoon.test.back.dao.interfaces.UserDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;
import ua.kiev.dimoon.test.back.dao.model.User;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

@Transactional
public class UserDAOImplTest extends BaseTestClass {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private RoleDAO roleDao;

    @Test
    public void testCreate() {
        Role role = createRole(null, TEST_ROLE_NAME);
        roleDao.create(role);
        Role role2 = createRole(null, TEST_ROLE_NAME);
        roleDao.create(role2);

        User user = createUser(null, TEST_USER_NAME);
        user.setRoles(Stream.of(role, role2).collect(Collectors.toSet()));
        userDao.create(user);
        assertThat(user.getId()).isNotNull();

        User user2 = userDao.findAll().stream()
                .filter(user1 -> user1.getId().equals(user.getId()))
                .findFirst().orElse(null);
        assertThat(user2).isNotNull();
        assertThat(user2.getRoles().size()).isEqualTo(2);
    }

    @Test
    public void testUpdate() {
        User user = createUser(null, TEST_USER_NAME);
        userDao.create(user);
        assertThat(user.getId()).isNotNull();
        user.setUserName("TEST");
        userDao.update(user);
        assertThat(userDao.findAll().stream()
                .anyMatch(user1 -> user1.getId().equals(user.getId()) && user1.getUserName().equals("TEST")))
                .isEqualTo(true);
    }

    @Test
    public void testRemove() {
        assertThat(userDao.findAll()).isEmpty();
        User user = createUser(null, TEST_USER_NAME);
        userDao.create(user);
        assertThat(user.getId()).isNotNull();
        assertThat(userDao.findAll().size()).isEqualTo(1);
        userDao.remove(user.getId());
        assertThat(userDao.findAll()).isEmpty();
    }

    @Test
    public void testFindUserByUsername() {
        User user = createUser(null, TEST_USER_NAME);
        userDao.create(user);
        assertThat(user.getId()).isNotNull();
        Optional<User> userByUsername = userDao.findUserByUsername(TEST_USER_NAME);
        assertThat(user.getId()).isEqualTo(userByUsername.map(User::getId).orElse(null));
    }

    @Test
    public void testFindUserByUsernameWithAbsentUser() {
        Optional<User> userByUsername = userDao.findUserByUsername("Wrong user name");
        assertThat(false).isEqualTo(userByUsername.isPresent());
    }

}
