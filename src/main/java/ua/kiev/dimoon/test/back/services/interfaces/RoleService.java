package ua.kiev.dimoon.test.back.services.interfaces;

import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;

import java.util.List;

/**
 * Created by admin on 22.03.2017.
 */
public interface RoleService {
    void create(RoleDto roleDto);
    void update(RoleDto roleDto);
    List<RoleDto> findAll();
    void remove(int id);
}
