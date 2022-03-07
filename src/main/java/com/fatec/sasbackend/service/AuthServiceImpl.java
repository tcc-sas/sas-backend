package com.fatec.sasbackend.service;

import com.fatec.sasbackend.model.auth.JwtModel;
import com.fatec.sasbackend.model.auth.UserLoginModel;
import com.fatec.sasbackend.repository.CrasRepository;
import com.fatec.sasbackend.repository.RoleRepository;
import com.fatec.sasbackend.repository.UserRepository;
import com.fatec.sasbackend.security.jwt.JwtUtils;
import com.fatec.sasbackend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, PasswordEncoder encoder, CrasRepository crasRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtModel authenticateUser(UserLoginModel model) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .get(0);

        return new JwtModel(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                role
        );
    }

}
