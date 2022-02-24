package com.fatec.sasbackend.service;

import com.fatec.sasbackend.model.auth.JwtModel;
import com.fatec.sasbackend.model.auth.UserLoginModel;
import com.fatec.sasbackend.model.auth.UserRegisterModel;

public interface AuthService {
    JwtModel authenticateUser(UserLoginModel model);

}
