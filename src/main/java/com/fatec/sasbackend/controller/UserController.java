package com.fatec.sasbackend.controller;

import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.model.user.UserRegisterModel;
import com.fatec.sasbackend.model.user.UserUpdateModel;
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

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterModel userRegisterModel) {

        Boolean status = userService.registerUser(userRegisterModel);

        if (Boolean.FALSE.equals(status)) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        return ResponseEntity.ok().body("User Registered Succesfully!");
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateModel userUpdateModel) {

        Boolean status = userService.updateUser(userUpdateModel);

        if (Boolean.FALSE.equals(status)) {
            return ResponseEntity.badRequest().body("Error: Update failed");
        }
        return ResponseEntity.ok().body("User Updated Succesfully!");
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
    public ResponseEntity<UserRegisterModel> findUserById(
            @PathVariable Long userId) {

        UserRegisterModel userRegisterModel = userService.findUserById(userId);
        return new ResponseEntity<>(userRegisterModel, HttpStatus.OK);
    }


    @GetMapping("/selectOptions")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserSelectOptionsDTO> findSelectOptions() {
        UserSelectOptionsDTO selectOptionsDTO = userService.findSelectOptions();
        return new ResponseEntity<>(selectOptionsDTO, HttpStatus.OK);
    }

      //@TODO
//    @DeleteMapping("/{userId}")

}
