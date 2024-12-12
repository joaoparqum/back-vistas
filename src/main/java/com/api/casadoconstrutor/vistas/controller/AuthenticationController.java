package com.api.casadoconstrutor.vistas.controller;

import com.api.casadoconstrutor.vistas.dto.AuthenticationDto;
import com.api.casadoconstrutor.vistas.dto.LoginDto;
import com.api.casadoconstrutor.vistas.dto.RegisterDto;
import com.api.casadoconstrutor.vistas.infra.security.TokenService;
import com.api.casadoconstrutor.vistas.repository.UserRepository;
import com.api.casadoconstrutor.vistas.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var user = (User) auth.getPrincipal();
            var token = tokenService.generateToken(user);
            System.out.println("Token gerado: " + token);

            return ResponseEntity.ok(new LoginDto(token, user.getRole().getRole(), user.getLogin()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Credenciais inválidas.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }


}
