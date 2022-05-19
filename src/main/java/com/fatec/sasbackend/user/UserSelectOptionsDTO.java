package com.fatec.sasbackend.user;

import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.role.RoleDTO;
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
