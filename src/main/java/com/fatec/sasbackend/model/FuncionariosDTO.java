package com.fatec.sasbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuncionariosDTO {

    private Long userId;
    private String name;
    private String username;
    private String roleName;
}
