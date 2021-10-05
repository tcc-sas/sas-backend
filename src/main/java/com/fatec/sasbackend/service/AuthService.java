package com.fatec.sasbackend.service;

import com.fatec.sasbackend.model.JwtModel;
import com.fatec.sasbackend.model.UserLoginModel;
import com.fatec.sasbackend.model.UserRegisterModel;

public interface AuthService {
    JwtModel authenticateUser(UserLoginModel model);
    Boolean registerUser(UserRegisterModel model);
}
