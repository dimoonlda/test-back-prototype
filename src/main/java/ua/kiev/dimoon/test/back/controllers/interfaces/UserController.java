package ua.kiev.dimoon.test.back.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;

import java.util.List;

/**
 * Created by admin on 22.03.2017.
 */
public interface UserController {
    ResponseEntity<List<UserDto>> getAllUsers();
    ResponseEntity addUser(UserDto userDto);
    ResponseEntity updateUser(UserDto userDto);
    ResponseEntity deleteUserById(Long id);
}
