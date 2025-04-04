package com.ptithcm.ejb.service;

import com.ptithcm.ejb.entity.User;
import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface UserServiceRemote {
    User create(String email, String password, String fullName) throws Exception;

    void update(User user) throws Exception;

    void delete(String id) throws Exception;

    User findById(String id) throws Exception;

    List<User> findAll();

    User findByEmail(String email);
}
