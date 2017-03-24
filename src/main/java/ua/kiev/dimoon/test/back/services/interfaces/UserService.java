package ua.kiev.dimoon.test.back.services.interfaces;

import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 22.03.2017.
 */
public interface UserService {
    void create(UserDto userDto);
    void update(UserDto userDto);
    List<UserDto> findAll();
    void remove(long id);
    Optional<User> findUserByUsername(String userName);
}
