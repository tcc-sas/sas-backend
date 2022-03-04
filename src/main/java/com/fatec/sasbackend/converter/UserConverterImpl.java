package com.fatec.sasbackend.converter;

import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.dto.RoleDTO;
import com.fatec.sasbackend.dto.UserDTO;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.entity.Role;
import com.fatec.sasbackend.entity.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Set;

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
        entity.setRoles(
                Set.of(
                    roleConverter.fromDtoToEntity(
                        new Role(), dto.getRoles())
                )
        );
        entity.setCras(
                crasConverter.fromDtoToEntity(
                        new Cras(), dto.getCras()
                )
        );
        return entity;
    }

    @Override
    public UserDTO fromEntityToDto(UserDTO dto, User entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUsername(entity.getUsername());
        dto.setRoles(
                roleConverter.fromEntityToDto(
                        new RoleDTO(), roleConverter.getFirstRole(
                                entity.getRoles()
                        )
                )
        );
        dto.setCras(
                crasConverter.fromEntityToDto(
                        new CrasDTO(), entity.getCras()
                )
        );
        return dto;
    }




}
