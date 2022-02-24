package com.fatec.sasbackend.service;

import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.model.auth.UserRegisterModel;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserService {
    Page<UserDTO> findAllUsers(Pageable pageable);
    Page<UserDTO> findPagedUsersByFilter(String name, String cras, Pageable pageable);
    UserSelectOptionsDTO findSelectOptions();
    UserDTO findUserById(Long userId);
    Boolean registerUser(UserRegisterModel model);
}
