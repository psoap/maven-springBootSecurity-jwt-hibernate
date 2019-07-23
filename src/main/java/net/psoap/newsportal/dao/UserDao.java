package net.psoap.newsportal.dao;

import net.psoap.newsportal.model.entity.User;

import java.util.Optional;

public interface UserDao extends AbstractDao<User, Long>{
    Optional<User> findUserByEmail(final String email);
    boolean existUserByEmail(final String email);
}