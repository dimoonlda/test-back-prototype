package ua.kiev.dimoon.test.back.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.kiev.dimoon.test.back.dao.interfaces.UserDAO;
import ua.kiev.dimoon.test.back.dao.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl extends AbstractDao implements UserDAO {
    Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public void create(User user) {
        getEntityManager().persist(user);
    }

    @Override
    public void update(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public List<User> findAll() {
        return getEntityManager().createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void remove(long id) {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        em.remove(user);
    }

    @Override
    public Optional<User> findUserByUsername(String userName) {
        try {
            User user = getEntityManager()
                    .createQuery("select u from User u where u.userName = ?1", User.class)
                    .setParameter(1, userName)
                    .getSingleResult();
            return Optional.of(user);
        }catch (NoResultException e) {
            LOG.debug("Username {} not found in db", userName);
            return Optional.empty();
        }
    }
}
