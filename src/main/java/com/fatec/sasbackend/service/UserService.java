package com.fatec.sasbackend.service;

import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserRegisterDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> findAllUsers(Pageable pageable);
    Page<UserDTO> findPagedUsersByFilter(String name, String cras, Pageable pageable);

    UserDTO findUserById(Long userId);
    UserDTO updateUser(UserDTO dto);

    UserSelectOptionsDTO findSelectOptions();
    UserRegisterDTO registerUser(UserRegisterDTO dto);
}
