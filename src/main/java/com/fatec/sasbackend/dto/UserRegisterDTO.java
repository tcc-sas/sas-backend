package com.fatec.sasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 45)
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 45, message = "Username max 45 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 8, message = "Password must contain between 6 and 8 characters")
    private String password;

    @NotNull(message = "Role cannot be null")
    private RoleDTO roles;

    @NotNull(message = "Cras cannot be null")
    private CrasDTO cras;
}
