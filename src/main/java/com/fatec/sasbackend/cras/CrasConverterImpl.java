package com.fatec.sasbackend.cras;

import org.springframework.stereotype.Component;

@Component
public class CrasConverterImpl implements CrasConverter {

    @Override
    public Cras fromDtoToEntity(Cras entity, CrasDTO dto) {
        entity.setId(dto.getId());
        entity.setName(convertNameToEnum(dto.getName()));
        return entity;
    }

    @Override
    public CrasDTO fromEntityToDto(CrasDTO dto, Cras entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName().toString());
        return dto;
    }

    private ECras convertNameToEnum(String name){
        try {
            return ECras.valueOf(name);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }


}
