package com.fatec.sasbackend.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtModel {

    private String token;
    private final String type = "Bearer";
    private Long id;
    private String username;
    private String name;
    private String role;

}