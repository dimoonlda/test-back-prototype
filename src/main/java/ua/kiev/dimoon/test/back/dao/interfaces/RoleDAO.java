package ua.kiev.dimoon.test.back.dao.interfaces;

import ua.kiev.dimoon.test.back.dao.model.Role;

import java.util.List;


public interface RoleDAO {
    void create(Role role);
    void update(Role role);
    List<Role> findAll();
    void remove(int id);
}
