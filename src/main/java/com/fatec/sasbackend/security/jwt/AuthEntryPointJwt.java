package com.fatec.sasbackend.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        String errorMessage = formatMessageResponse(authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
    }

    private String formatMessageResponse(String message){
        return switch (message) {
            case ("Bad credentials") -> "Login ou senha inválidos!";
            case ("Full authentication is required to access this resource") -> "Sua Sessão expirou! Realize o acesso novamente";
            default -> message;
        };

    }



}
