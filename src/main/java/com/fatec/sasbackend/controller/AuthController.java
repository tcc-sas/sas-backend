package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.model.auth.JwtModel;
import com.fatec.sasbackend.model.auth.UserLoginModel;
import com.fatec.sasbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

//    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {
//
//        Boolean status = authService.registerUser(userRegisterModel);
//
//        if(!status){
//            return ResponseEntity.badRequest().body("Error: Username is already taken!");
//        }
//        return ResponseEntity.ok().body("User Registered Succesfully!");
//    }


}