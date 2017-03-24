package ua.kiev.dimoon.test.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kiev.dimoon.test.back.controllers.interfaces.UserController;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Autowired
    public UserControllerImpl setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addUser(@RequestBody @Valid UserDto userDto) {
        userService.create(userDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody @Valid UserDto userDto) {
        if (Objects.isNull(userDto.getId())) {
            throw new IllegalArgumentException("field id is required.");
        }
        userService.update(userDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(@PathVariable("id") Long id) {
        userService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
