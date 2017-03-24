package ua.kiev.dimoon.test.back.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by admin on 22.03.2017.
 */
public abstract class AbstractDao {

    private EntityManager entityManager;

    @PersistenceContext
    public AbstractDao setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
