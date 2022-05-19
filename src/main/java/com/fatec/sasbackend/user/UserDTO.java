package com.fatec.sasbackend.user;

import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.role.RoleDTO;
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
public class UserDTO {
    private Long id;

    @NotBlank(message = "O nome não pode ser nulo")
    @Size(max = 45)
    private String name;

    @NotBlank(message = "O usuário não pode ser nulo")
    @Size(max = 45)
    private String username;

    @NotNull(message = "O nível de permissão não pode ser nulo")
    private RoleDTO roles;

    @NotNull(message = "A unidade não pode ser nula")
    private CrasDTO cras;
}
