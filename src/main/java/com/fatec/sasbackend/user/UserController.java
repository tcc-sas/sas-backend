package com.fatec.sasbackend.user;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findAllPagedUsers(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserDTO> userDTO = userService.findAllUsers(pageable);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findPagedUsersByFilter(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "cras") String cras,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserDTO> filteredUsers = userService.findPagedUsersByFilter(name, cras, pageable);
        return ResponseEntity.ok(filteredUsers);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRegisterDTO> registerUser(@Valid @RequestBody UserRegisterDTO dto) {

        UserRegisterDTO userDTO = userService.registerUser(dto);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> findUserById(
            @PathVariable Long userId) {

        UserDTO userDTO = userService.findUserById(userId);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO user = userService.updateUser(userDto);
        return ResponseEntity.ok().body(user);
    }

    //@TODO
    //@DeleteMapping("/{userId}")

    @GetMapping("/select-options")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSelectOptionsDTO> findSelectOptions() {
        UserSelectOptionsDTO selectOptionsDTO = userService.findSelectOptions();
        return new ResponseEntity<>(selectOptionsDTO, HttpStatus.OK);
    }












}
