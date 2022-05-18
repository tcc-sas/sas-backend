package com.fatec.sasbackend.service;

import com.fatec.sasbackend.converter.BeneficiaryConverter;
import com.fatec.sasbackend.converter.CrasConverter;
import com.fatec.sasbackend.dto.BeneficiaryDTO;
import com.fatec.sasbackend.dto.BeneficiarySelectOptionsDTO;
import com.fatec.sasbackend.dto.CrasDTO;
import com.fatec.sasbackend.entity.Beneficiary;
import com.fatec.sasbackend.entity.Cras;
import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import com.fatec.sasbackend.repository.BeneficiaryRepository;
import com.fatec.sasbackend.repository.CrasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
    public BeneficiaryDTO registerBeneficiary(BeneficiaryDTO beneficiaryDTO) {

        if (repository.existsByCpf(beneficiaryDTO.getCpf())) {
            throw new AlreadyExistsException("This CPF cannot be used");
        }

        Beneficiary beneficiary = converter.fromDtoToEntity(new Beneficiary(), beneficiaryDTO);
        repository.save(beneficiary);

        return beneficiaryDTO;
    }

    @Override
    public Page<BeneficiaryDTO> findAllBeneficiary(Pageable pageable) {
        return repository.findAll(pageable)
                .map(b -> converter.fromEntityToDto(new BeneficiaryDTO(), b));
    }

    @Override
    public BeneficiaryDTO findById(Long id) {
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
    public Page<BeneficiaryDTO> findPagedBeneficiaryByFilter(String name, String rg, String cpf, String cras, Pageable pageable) {
        return repository.findPagedBeneficiaryByFilter(
                        name.toLowerCase(),
                        rg,
                        cpf,
                        cras,
                        pageable
                )
                .map(beneficiaries -> converter.fromEntityToDto(new BeneficiaryDTO(), beneficiaries));
    }

    @Override
    @Transactional
    public BeneficiaryDTO updateBeneficiary(BeneficiaryDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new BadRequestException("Beneficiary ID cannot be null");
        }

        if(repository.checkIfCpfAlreadyUsed(dto.getId(),dto.getCpf())){
            throw new AlreadyExistsException("This CPF cannot be used");
        }

        Beneficiary entity = repository.findById(dto.getId())
                .map(beneficiary -> converter.fromDtoToEntity(beneficiary, dto))
                .orElseThrow(() -> new NotFoundException("Beneficiary not found"));

        return converter.fromEntityToDto(dto, entity);
    }
}
