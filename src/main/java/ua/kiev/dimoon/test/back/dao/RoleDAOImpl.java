package ua.kiev.dimoon.test.back.dao;

import org.springframework.stereotype.Repository;
import ua.kiev.dimoon.test.back.dao.interfaces.RoleDAO;
import ua.kiev.dimoon.test.back.dao.model.Role;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleDAOImpl extends AbstractDao implements RoleDAO {

    @Override
    public void create(Role role) {
        getEntityManager().persist(role);
    }

    @Override
    public void update(Role role) {
        getEntityManager().merge(role);
    }

    @Override
    public List<Role> findAll() {
        return getEntityManager().createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void remove(int id) {
        EntityManager em = getEntityManager();
        Role role = em.find(Role.class, id);
        em.remove(role);
    }
}
