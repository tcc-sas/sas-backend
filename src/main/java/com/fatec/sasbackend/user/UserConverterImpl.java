package com.fatec.sasbackend.user;

import com.fatec.sasbackend.cras.CrasConverter;
import com.fatec.sasbackend.role.RoleConverter;
import com.fatec.sasbackend.role.RoleConverterImpl;
import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.role.RoleDTO;
import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.role.Role;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {
    private final RoleConverter roleConverter;
    private final CrasConverter crasConverter;

    public UserConverterImpl(RoleConverterImpl roleConverter, CrasConverter crasConverter) {
        this.roleConverter = roleConverter;
        this.crasConverter = crasConverter;
    }

    @Override
    public User fromDtoToEntity(User entity, UserDTO dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setRoles(roleConverter.fromDtoToEntity(new Role(), dto.getRoles()));
        entity.setCras(crasConverter.fromDtoToEntity(new Cras(), dto.getCras()));
        return entity;
    }

    @Override
    public UserDTO fromEntityToDto(UserDTO dto, User entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUsername(entity.getUsername());
        dto.setRoles(roleConverter.fromEntityToDto(new RoleDTO(), entity.getRoles()));
        dto.setCras(crasConverter.fromEntityToDto(new CrasDTO(), entity.getCras()));
        return dto;
    }




}
