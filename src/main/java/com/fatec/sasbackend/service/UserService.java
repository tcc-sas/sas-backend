package com.fatec.sasbackend.service;

import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.dto.UserSelectOptionsDTO;
import com.fatec.sasbackend.model.user.UserRegisterModel;
import com.fatec.sasbackend.model.user.UserUpdateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> findAllUsers(Pageable pageable);
    Page<UserDTO> findPagedUsersByFilter(String name, String cras, Pageable pageable);
    UserSelectOptionsDTO findSelectOptions();
    UserRegisterModel findUserById(Long userId);
    Boolean registerUser(UserRegisterModel model);
    Boolean updateUser(UserUpdateModel model);
}
