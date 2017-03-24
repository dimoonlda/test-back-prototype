package ua.kiev.dimoon.test.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dimoon.test.back.dao.interfaces.RoleDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.services.interfaces.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDAO roleDao;

    @Autowired
    public RoleServiceImpl setRoleDao(RoleDAO roleDao) {
        this.roleDao = roleDao;
        return this;
    }

    @Override
    @Transactional
    public void create(RoleDto roleDto) {
        Role role = new Role()
                .setName(roleDto.getName());
        roleDao.create(role);
    }

    @Override
    @Transactional
    public void update(RoleDto roleDto) {
        Role role = new Role()
                .setId(roleDto.getId())
                .setName(roleDto.getName());
        roleDao.update(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        return roleDao.findAll().stream().map(Role::getDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(int id) {
        roleDao.remove(id);
    }
}
