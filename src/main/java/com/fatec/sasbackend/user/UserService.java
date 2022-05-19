package com.fatec.sasbackend.user;

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
