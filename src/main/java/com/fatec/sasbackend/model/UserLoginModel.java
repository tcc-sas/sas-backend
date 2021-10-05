package com.fatec.sasbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginModel {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
