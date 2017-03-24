package ua.kiev.dimoon.test.back.dao;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.dimoon.test.back.BaseTestClass;
import ua.kiev.dimoon.test.back.dao.interfaces.RoleDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;

import static ua.kiev.dimoon.test.back.TestObjectFactory.*;

@Transactional
public class RoleDAOImplTest extends BaseTestClass {

    @Autowired
    private RoleDAO roleDao;

    @Test
    public void testCreate() {
        Role role = createRole(null, TEST_ROLE_NAME);
        roleDao.create(role);
        assertThat(role.getId()).isNotNull();
    }

    @Test
    public void testUpdate() {
        Role role = createRole(null, TEST_ROLE_NAME);
        roleDao.create(role);
        assertThat(role.getId()).isNotNull();
        role.setName("TEST");
        roleDao.update(role);
        assertThat(roleDao.findAll().stream()
                .anyMatch(role1 -> role1.getId().equals(role.getId()) && role1.getName().equals("TEST")))
                .isEqualTo(true);
    }

    @Test
    public void testRemove() {
        assertThat(roleDao.findAll()).isEmpty();
        Role role = createRole(null, TEST_ROLE_NAME);
        roleDao.create(role);
        assertThat(role.getId()).isNotNull();
        assertThat(roleDao.findAll().size()).isEqualTo(1);
        roleDao.remove(role.getId());
        assertThat(roleDao.findAll()).isEmpty();
    }

}
