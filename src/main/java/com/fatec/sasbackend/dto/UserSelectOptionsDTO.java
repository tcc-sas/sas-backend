package com.fatec.sasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelectOptionsDTO {
    private List<RoleDTO> roles;
    private List<CrasDTO> cras;
}
