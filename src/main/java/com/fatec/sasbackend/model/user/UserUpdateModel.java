package com.fatec.sasbackend.model.user;

import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateModel {

    private Long userId;

    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    private CrasDTO cras;

    @NotNull
    private RoleDTO roles;

}
