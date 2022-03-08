package com.fatec.sasbackend.converter;

import com.fatec.sasbackend.dto.BeneficiaryDTO;
import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.entity.Beneficiary;
import com.fatec.sasbackend.entity.Cras;
import org.springframework.stereotype.Component;

@Component
public class BeneficiaryConverterImpl implements BeneficiaryConverter {

    private final CrasConverter crasConverter;

    public BeneficiaryConverterImpl(CrasConverter crasConverter) {
        this.crasConverter = crasConverter;
    }

    @Override
    public Beneficiary fromDtoToEntity(Beneficiary entity, BeneficiaryDTO dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBeneficiaryId(dto.getBeneficiaryId());
        entity.setAdress(dto.getAdress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setZipCode(dto.getZipCode());
        entity.setCras(crasConverter.fromDtoToEntity(new Cras(), dto.getCras()));
        return entity;
    }

    @Override
    public BeneficiaryDTO fromEntityToDto(BeneficiaryDTO dto, Beneficiary entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBeneficiaryId(entity.getBeneficiaryId());
        dto.setAdress(entity.getAdress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setZipCode(entity.getZipCode());
        dto.setCras(crasConverter.fromEntityToDto(new CrasDTO(), entity.getCras()));
        return dto;
    }
}
