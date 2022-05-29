package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.cras.CrasConverter;
import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.cras.Cras;
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
        entity.setRg(dto.getRg());
        entity.setCpf(dto.getCpf());

        entity.setZipCode(dto.getZipCode());
        entity.setAddress(dto.getAddress());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setDistrict(dto.getDistrict());
        entity.setCity(dto.getCity());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthDate(dto.getBirthDate());


        entity.setCras(crasConverter.fromDtoToEntity(new Cras(), dto.getCras()));
        return entity;
    }

    @Override
    public BeneficiaryDTO fromEntityToDto(BeneficiaryDTO dto, Beneficiary entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRg(entity.getRg());
        dto.setCpf(entity.getCpf());

        dto.setZipCode(entity.getZipCode());
        dto.setAddress(entity.getAddress());
        dto.setHouseNumber(entity.getHouseNumber());
        dto.setDistrict(entity.getDistrict());
        dto.setCity(entity.getCity());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setBirthDate(entity.getBirthDate());

        dto.setCras(crasConverter.fromEntityToDto(new CrasDTO(), entity.getCras()));
        return dto;
    }
}
