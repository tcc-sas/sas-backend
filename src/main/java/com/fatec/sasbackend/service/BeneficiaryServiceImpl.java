package com.fatec.sasbackend.service;

import com.fatec.sasbackend.converter.BeneficiaryConverter;
import com.fatec.sasbackend.converter.CrasConverter;
import com.fatec.sasbackend.dto.BeneficiaryDTO;
import com.fatec.sasbackend.dto.BeneficiarySelectOptionsDTO;
import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.entity.Beneficiary;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.repository.BeneficiaryRepository;
import com.fatec.sasbackend.repository.CrasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository repository;
    private final BeneficiaryConverter converter;
    private final CrasRepository crasRepository;
    private final CrasConverter crasConverter;

    public BeneficiaryServiceImpl(BeneficiaryRepository repository, BeneficiaryConverter converter, CrasRepository crasRepository, CrasConverter crasConverter) {
        this.repository = repository;
        this.converter = converter;
        this.crasRepository = crasRepository;
        this.crasConverter = crasConverter;
    }

    @Override
    public Boolean registerBeneficiary(BeneficiaryDTO beneficiaryDTO) {

        if (Boolean.TRUE.equals(repository.existsByBeneficiaryId(beneficiaryDTO.getBeneficiaryId()))) {
            return false;
        }

        Beneficiary beneficiary = converter.fromDtoToEntity(new Beneficiary(), beneficiaryDTO);
        repository.save(beneficiary);

        return true;
    }

    @Override
    public Page<BeneficiaryDTO> findAllBeneficiary(Pageable pageable) {
        List<BeneficiaryDTO> beneficiaryDTOs = repository.findAll(pageable)
                .stream()
                .map(user -> converter.fromEntityToDto(new BeneficiaryDTO(), user))
                .toList();

        return new PageImpl<>(beneficiaryDTOs);
    }

    @Override
    public BeneficiaryDTO findBeneficiaryById(Long id) {
        return repository.findById(id)
                .map(beneficiary ->
                        converter.fromEntityToDto(new BeneficiaryDTO(), beneficiary))
                .orElseThrow(() ->
                        new RuntimeException("Beneficiary Not Found!"));
    }

    @Override
    public BeneficiarySelectOptionsDTO findSelectOptions() {
        List<CrasDTO> crasDTOS = crasRepository
                .findAll()
                .stream()
                .distinct()
                .sorted(Comparator.comparing(Cras::getName).reversed())
                .map(cras -> crasConverter.fromEntityToDto(new CrasDTO(), cras))
                .toList();

        return new BeneficiarySelectOptionsDTO(crasDTOS);
    }

    @Override
    public Page<BeneficiaryDTO> findPagedBeneficiaryByFilter(String name, String beneficiaryId, String cras, Pageable pageable) {
        List<BeneficiaryDTO> beneficiariesDTO = repository.findPagedBeneficiaryByFilter(
                        name.toLowerCase(),
                        beneficiaryId,
                        cras,
                        pageable
                ).stream()
                .map(beneficiaries -> converter.fromEntityToDto(new BeneficiaryDTO(), beneficiaries))
                .toList();

        return new PageImpl<>(beneficiariesDTO);
    }

    @Override
    public Boolean updateBeneficiary(BeneficiaryDTO model) {
        return null;
    }
}
