package com.api.casadoconstrutor.vistas.controller;

import com.api.casadoconstrutor.vistas.service.UserService;
import com.api.casadoconstrutor.vistas.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.findAll());
    }
}
