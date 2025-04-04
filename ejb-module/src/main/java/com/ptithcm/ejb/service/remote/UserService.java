package com.ptithcm.ejb.service.remote;

import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.enums.AccountStatusEnum;
import com.ptithcm.ejb.service.AuthServiceRemote;
import com.ptithcm.ejb.service.UserServiceRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;

import static com.ptithcm.ejb.utils.PasswordUtil.hashPassword;

@Stateless
public class UserService implements UserServiceRemote {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private AuthServiceRemote authService;

    @Override
    public User create(String email, String password, String fullName) throws Exception {
        if (findByEmail(email) != null) {
            throw new Exception("Email already exists");
        }

        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(hashPassword(password));
            user.setFullName(fullName);

            entityManager.persist(user);

            return user;
        } catch (PersistenceException e) {
            throw new Exception("Cannot create user", e);
        }
    }

    @Override
    public void update(User user) throws Exception {
        User existingUser = entityManager.find(User.class, user.getId());
        if (existingUser == null) {
            throw new Exception("User not found");
        }

        if (user.getPassword().isBlank()) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(hashPassword(user.getPassword()));
        }

        entityManager.merge(user);
    }

    @Override
    public void delete(String id) throws Exception {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new Exception("User not found");
        }
        user.setStatus(AccountStatusEnum.CANCELED);
        entityManager.merge(user);
    }

    @Override
    public User findById(String id) throws Exception {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM users u", User.class).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        try {
            return entityManager
                    .createQuery("SELECT u FROM users u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}




