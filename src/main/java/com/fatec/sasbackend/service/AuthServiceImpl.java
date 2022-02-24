package com.fatec.sasbackend.service;

import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.dto.RoleDTO;
import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.entity.User;
import com.fatec.sasbackend.enums.ECras;
import com.fatec.sasbackend.enums.ERole;
import com.fatec.sasbackend.model.auth.JwtModel;
import com.fatec.sasbackend.model.auth.UserLoginModel;
import com.fatec.sasbackend.model.auth.UserRegisterModel;
import com.fatec.sasbackend.repository.CrasRepository;
import com.fatec.sasbackend.repository.RoleRepository;
import com.fatec.sasbackend.repository.UserRepository;
import com.fatec.sasbackend.security.jwt.JwtUtils;
import com.fatec.sasbackend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final CrasRepository crasRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RoleRepository roleRepository, PasswordEncoder encoder, CrasRepository crasRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.crasRepository = crasRepository;
    }

    @Override
    public JwtModel authenticateUser(UserLoginModel model) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtModel(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getName(),
                roles);
    }

//    @Override
//    public Boolean registerUser(UserRegisterModel model) {
//        if (userRepository.existsByUsername(model.getUsername())) {
//            return false;
//        }
//
//        Set<Role> roles = findRoleByName(model.getRole());
//        Cras cras = findCrasById(model.getCras());
//
//
//        User user = new User(0L, model.getUsername(), model.getName(), encoder.encode(model.getPassword()), cras, roles);
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return true;
//    }
//
//    private Set<Role> findRoleByName(RoleDTO role) {
//        Set<Role> roles = new HashSet<>();
//
//        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//
//        if(role.getName().equalsIgnoreCase("admin")){
//            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
//            roles.add(adminRole);
//            roles.add(userRole);
//        } else {
//            roles.add(userRole);
//        }
//
//        return roles;
//    }
//
//    private Cras findCrasById(CrasDTO crasDTO) {
//        Cras cras = null;
//
//        if(crasDTO.getId() != null){
//            cras = crasRepository.findById(cras.getId()).orElseThrow(
//                    () -> new RuntimeException("Error: Cras not found")
//            );
//        }
//        return cras;
//    }
}
