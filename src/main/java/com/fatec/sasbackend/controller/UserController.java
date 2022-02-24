package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.model.auth.UserRegisterModel;
import com.fatec.sasbackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private static final String NULL = null;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {

        Boolean status = userService.registerUser(userRegisterModel);

        if (!status) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        return ResponseEntity.ok().body("User Registered Succesfully!");
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findAllPagedUsers(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserDTO> userDTO = userService.findAllUsers(pageable);

        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findPagedUsersByFilter(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "cras") String cras,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserDTO> filteredUsers = userService.findPagedUsersByFilter(name, cras, pageable);
        return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> findUserById(
            @PathVariable Long userId) {

        UserDTO userDto = userService.findUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @GetMapping("/selectOptions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserSelectOptionsDTO> findSelectOptions() {
        UserSelectOptionsDTO selectOptionsDTO = userService.findSelectOptions();
        return new ResponseEntity<>(selectOptionsDTO, HttpStatus.OK);
    }

}
