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
        entity.setBeneficiaryId(dto.getBeneficiaryId());
        entity.setName(dto.getName());
        entity.setRg(dto.getRg());
        entity.setCpf(dto.getCpf());

        entity.setZipCode(dto.getZipCode());
        entity.setAdress(dto.getAdress());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setDistrict(dto.getDistrict());
        entity.setCity(dto.getCity());
        entity.setPhoneNumber(dto.getPhoneNumber());

        entity.setCras(crasConverter.fromDtoToEntity(new Cras(), dto.getCras()));
        return entity;
    }

    @Override
    public BeneficiaryDTO fromEntityToDto(BeneficiaryDTO dto, Beneficiary entity) {
        dto.setBeneficiaryId(entity.getBeneficiaryId());
        dto.setName(entity.getName());
        dto.setRg(entity.getRg());
        dto.setCpf(entity.getCpf());

        dto.setZipCode(entity.getZipCode());
        dto.setAdress(entity.getAdress());
        dto.setHouseNumber(entity.getHouseNumber());
        dto.setDistrict(entity.getDistrict());
        dto.setCity(entity.getCity());
        dto.setPhoneNumber(entity.getPhoneNumber());

        dto.setCras(crasConverter.fromEntityToDto(new CrasDTO(), entity.getCras()));
        return dto;
    }
}
