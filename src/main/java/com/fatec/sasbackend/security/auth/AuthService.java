package com.fatec.sasbackend.security.auth;

import com.fatec.sasbackend.security.auth.JwtModel;
import com.fatec.sasbackend.security.auth.UserLoginModel;

public interface AuthService {
    JwtModel authenticateUser(UserLoginModel model);

}
