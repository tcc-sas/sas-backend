package com.fatec.sasbackend.converter;

import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.enums.ECras;
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
