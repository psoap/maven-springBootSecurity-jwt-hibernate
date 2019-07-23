package net.psoap.newsportal.dao;


import java.io.Serializable;
import java.util.Optional;

public interface AbstractDao<T extends Serializable, Id extends Serializable> {
    T insert(final T entity);

    T update(final T entity);

    void delete(final T entity);

    Optional<T> findOneById(final Id id);
}
