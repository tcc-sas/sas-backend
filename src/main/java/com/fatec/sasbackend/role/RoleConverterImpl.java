package com.fatec.sasbackend.role;

import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role fromDtoToEntity(Role entity, RoleDTO dto) {
        entity.setId(dto.getId());
        entity.setName(
                converNameToEnum(
                        dto.getName()
                )
        );
        return entity;

    }

    @Override
    public RoleDTO fromEntityToDto(RoleDTO dto, Role entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName().toString());
        return dto;
    }

    private ERole converNameToEnum(String name){
        try {
            return ERole.valueOf(name);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }
}
