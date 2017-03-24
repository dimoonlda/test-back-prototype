package ua.kiev.dimoon.test.back;

import ua.kiev.dimoon.test.back.dao.model.Role;
import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;

public class TestObjectFactory {
    public static final String TEST_ROLE_NAME = "TEST_NAME";
    public static final String TEST_USER_NAME = "test name";
    public static final String TEST_USER_PASS = "pass";

    public static Role createRole(Integer id, String name) {
        return new Role()
                .setId(id)
                .setName(name);
    }

    public static RoleDto createRoleDto(Integer id, String name) {
        return new RoleDto()
                .setId(id)
                .setName(name);
    }

    public static User createUser(Long id, String userName) {
        return new User()
                .setId(id)
                .setUserName(userName)
                .setPassWord(TEST_USER_PASS)
                .setActive(true);
    }

    public static UserDto createUserDto(Long id, String userName) {
        return new UserDto()
                .setId(id)
                .setUserName(userName)
                .setPassWord(TEST_USER_PASS)
                .setActive(true);
    }
}
