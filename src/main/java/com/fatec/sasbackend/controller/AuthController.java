package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.entity.User;
import com.fatec.sasbackend.enums.ERole;
import com.fatec.sasbackend.model.JwtModel;
import com.fatec.sasbackend.model.UserLoginModel;
import com.fatec.sasbackend.model.UserRegisterModel;
import com.fatec.sasbackend.repository.RoleRepository;
import com.fatec.sasbackend.repository.UserRepository;
import com.fatec.sasbackend.security.jwt.JwtUtils;
import com.fatec.sasbackend.security.services.UserDetailsImpl;
import com.fatec.sasbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<JwtModel> authenticateUser(@Valid @RequestBody UserLoginModel loginModel) {

        JwtModel jwt = authService.authenticateUser(loginModel);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {

        Boolean status = authService.registerUser(userRegisterModel);

        if(!status){
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        return ResponseEntity.ok().body("User Registered Succesfully!");
    }
}