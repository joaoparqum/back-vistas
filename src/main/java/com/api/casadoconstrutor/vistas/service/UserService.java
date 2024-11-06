package com.api.casadoconstrutor.vistas.service;

import com.api.casadoconstrutor.vistas.repository.UserRepository;
import com.api.casadoconstrutor.vistas.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }


}
