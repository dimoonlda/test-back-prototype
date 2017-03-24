package ua.kiev.dimoon.test.back.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dimoon.test.back.dao.interfaces.UserDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;
import ua.kiev.dimoon.test.back.dao.model.User;
import ua.kiev.dimoon.test.back.dao.model.dto.UserDto;
import ua.kiev.dimoon.test.back.services.interfaces.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDao;

    @Autowired
    public UserServiceImpl setUserDao(UserDAO userDao) {
        this.userDao = userDao;
        return this;
    }

    @Override
    public void create(UserDto userDto) {
        User user = new User()
                .setUserName(userDto.getUserName())
                .setPassWord(userDto.getPassWord())
                .setActive(userDto.getActive());
        if (Objects.nonNull(userDto.getRoles()) && !userDto.getRoles().isEmpty()) {
            user.setRoles(userDto.getRoles().stream()
                    .map(roleDto -> new Role()
                            .setId(roleDto.getId())
                            .setName(roleDto.getName()))
                    .collect(Collectors.toSet()));
        }
        userDao.create(user);
    }

    @Override
    public void update(UserDto userDto) {
        User user = new User()
                .setId(userDto.getId())
                .setUserName(userDto.getUserName())
                .setPassWord(userDto.getPassWord())
                .setActive(userDto.getActive());
        if (Objects.nonNull(userDto.getRoles()) && !userDto.getRoles().isEmpty()) {
            user.setRoles(userDto.getRoles().stream()
                    .map(roleDto -> new Role()
                            .setId(roleDto.getId())
                            .setName(roleDto.getName()))
                    .collect(Collectors.toSet()));
        }
        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(User::getDto).collect(Collectors.toList());
    }

    @Override
    public void remove(long id) {
        userDao.remove(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String userName){
        return userDao.findUserByUsername(userName);
    }
}
