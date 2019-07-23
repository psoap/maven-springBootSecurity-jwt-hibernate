package net.psoap.newsportal.dao.impl.hibernate;

import net.psoap.newsportal.dao.UserDao;
import net.psoap.newsportal.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userDao")
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public Optional<User> findUserByEmail(String email) {
        return getEntityManager().createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public boolean existUserByEmail(String email) {
        return findUserByEmail(email).isPresent();
    }

}