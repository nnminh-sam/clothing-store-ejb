package com.ptithcm.ejb.service.remote;

import com.ptithcm.ejb.entity.User;
import com.ptithcm.ejb.service.AuthServiceRemote;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import org.mindrot.jbcrypt.BCrypt;


@Stateless
public class AuthService implements AuthServiceRemote {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User login(String email, String password) throws Exception {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new Exception("Invalid email");
        }

        try {
            TypedQuery<User> query = entityManager
                    .createQuery("SELECT u FROM users u WHERE u.email = :email", User.class)
                    .setParameter("email", email);
            User user = query.getSingleResult();

            if (!BCrypt.checkpw(password, user.getPassword())) {
                throw new Exception("Wrong password");
            }

            return user;
        } catch (NoResultException e) {
            throw new Exception("Invalid credentials");
        }
    }
}
