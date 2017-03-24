package ua.kiev.dimoon.test.back.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.kiev.dimoon.test.back.dao.interfaces.RoleDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;
import ua.kiev.dimoon.test.back.dao.model.dto.RoleDto;
import ua.kiev.dimoon.test.back.services.interfaces.RoleService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ua.kiev.dimoon.test.back.TestObjectFactory.TEST_ROLE_NAME;
import static ua.kiev.dimoon.test.back.TestObjectFactory.createRole;
import static ua.kiev.dimoon.test.back.TestObjectFactory.createRoleDto;

public class RoleServiceImplTest{

    @Mock
    private RoleDAO roleDao;

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Mockito.doNothing().when(roleDao).create(any(Role.class));
        RoleDto roleDto = createRoleDto(null, TEST_ROLE_NAME);
        roleService.create(roleDto);
        Mockito.verify(roleDao).create(any(Role.class));
    }

    @Test
    public void testUpdate() {
        Mockito.doNothing().when(roleDao).update(any(Role.class));
        RoleDto roleDto = createRoleDto(1, TEST_ROLE_NAME);
        roleService.update(roleDto);
        Mockito.verify(roleDao).update(any(Role.class));
    }

    @Test
    public void testFindAll() {
        Role role1 = createRole(1, TEST_ROLE_NAME);
        Role role2 = createRole(2, TEST_ROLE_NAME);
        List<Role> rolesFromDao = Arrays.asList(role1, role2);
        when(roleDao.findAll()).thenReturn(rolesFromDao);
        List<RoleDto> rolesDtoResult = roleService.findAll();
        assertThat(rolesDtoResult.size()).isEqualTo(rolesFromDao.size());
        Mockito.verify(roleDao).findAll();
    }

    @Test
    public void testRemove() {
        Mockito.doNothing().when(roleDao).remove(1);
        roleService.remove(1);
        Mockito.verify(roleDao).remove(1);
    }
}
