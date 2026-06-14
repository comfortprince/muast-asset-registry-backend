// UserAuthRepositoryImpl.java
package ac.muast.it.asset_register.security;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ac.muast.it.asset_register.model.User;

import java.util.Optional;

@Repository
public class UserAuthRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE u.username = :username", User.class)
            .setParameter("username", username)
            .getResultStream()
            .findFirst();
    }

    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        }
        return entityManager.merge(user);
    }

}