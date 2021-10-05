package com.fatec.sasbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterModel {

    @NotBlank
    @Size(min = 3, max = 45)
    private String username;

    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotBlank
    @Size(min = 6, max = 8)
    private String password;

    private Set<String> role;

}
