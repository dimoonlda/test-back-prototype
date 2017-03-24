package ua.kiev.dimoon.test.back.dao.interfaces;

import ua.kiev.dimoon.test.back.dao.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void create(User user);
    void update(User user);
    List<User> findAll();
    void remove(long id);
    Optional<User> findUserByUsername(String userName);
}
