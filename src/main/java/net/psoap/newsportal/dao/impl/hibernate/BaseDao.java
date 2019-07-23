package net.psoap.newsportal.dao.impl.hibernate;

import net.psoap.newsportal.dao.AbstractDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class BaseDao <T extends Serializable> implements AbstractDao<T, Long> {
    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T insert(final T entity){
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T update(final T entity){
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(final T entity){
        entityManager.remove(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<T> findOneById(final Long id){
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }
}