package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.cras.CrasConverter;
import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import com.fatec.sasbackend.cras.CrasRepository;
import com.fatec.sasbackend.product.ProductRepository;
import com.fatec.sasbackend.product.SimpleProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    public static final String BENEFICIARY_NOT_FOUND = "Beneficiário não encontrado!";
    public static final String CPF_ALREADY_TAKEN = "CPF já cadastrado";

    private final BeneficiaryRepository repository;
    private final BeneficiaryConverter converter;
    private final CrasRepository crasRepository;
    private final CrasConverter crasConverter;

    private final ProductRepository productRepository;

    private final BeneficiaryProductsRepository beneficiaryProductsRepository;

    public BeneficiaryServiceImpl(BeneficiaryRepository repository, BeneficiaryConverter converter, CrasRepository crasRepository, CrasConverter crasConverter, ProductRepository productRepository, BeneficiaryProductsRepository beneficiaryProductsRepository) {
        this.repository = repository;
        this.converter = converter;
        this.crasRepository = crasRepository;
        this.crasConverter = crasConverter;
        this.productRepository = productRepository;
        this.beneficiaryProductsRepository = beneficiaryProductsRepository;
    }

    @Override
    public BeneficiaryDTO registerBeneficiary(BeneficiaryDTO beneficiaryDTO) {

        if (Boolean.TRUE.equals(repository.existsByCpf(beneficiaryDTO.getCpf()))) {
            throw new AlreadyExistsException(CPF_ALREADY_TAKEN);
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
                        new NotFoundException(BENEFICIARY_NOT_FOUND));
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

        if (Boolean.TRUE.equals(repository.checkIfCpfAlreadyUsed(dto.getId(), dto.getCpf()))) {
            throw new AlreadyExistsException(CPF_ALREADY_TAKEN);
        }

        Beneficiary entity = repository.findById(dto.getId())
                .map(beneficiary -> converter.fromDtoToEntity(beneficiary, dto))
                .orElseThrow(() -> new NotFoundException(BENEFICIARY_NOT_FOUND));

        return converter.fromEntityToDto(dto, entity);
    }


    @Override
    @Transactional
    public BeneficiaryProductsDTO registerBeneficiaryProducts(BeneficiaryProductsDTO beneficiaryProductsDTO) {
        Beneficiary beneficiary = repository.getById(beneficiaryProductsDTO.getBeneficiaryId());

        List<BeneficiaryProducts> beneficiaryProductsList = new ArrayList<>(
                beneficiaryProductsDTO.getProductsDTO()
                        .stream()
                        .map(dto -> BeneficiaryProducts.builder()
                                .beneficiary(beneficiary)
                                .product(productRepository.getById(dto.getId()))
                                .quantity(dto.getQuantity())
                                .build()
                        ).toList()
        );

        beneficiaryProductsRepository.deleteAllByBeneficiaryId(beneficiary.getId());
        beneficiaryProductsRepository.saveAll(beneficiaryProductsList);

        return beneficiaryProductsDTO;
    }


    public BeneficiaryProductsDTO findBeneficiaryProducts(Long id){
       List<SimpleProductDTO> simpleProductDTOS = beneficiaryProductsRepository.findAllByBeneficiaryId(id)
               .stream()
               .map(p -> SimpleProductDTO.builder()
                       .id(p.getProduct().getId())
                       .name(p.getProduct().getName())
                       .quantity(p.getQuantity())
                       .build())
               .toList();


       return BeneficiaryProductsDTO.builder()
               .beneficiaryId(id)
               .productsDTO(simpleProductDTOS)
               .build();
    }

    @Override
    public Long benefitBeneficiary(Long id) {
        return null;
    }

    @Override
    public void deleteBeneficiary(String id) {
        repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException(BENEFICIARY_NOT_FOUND));

        repository.deleteById(Long.parseLong(id));
    }


}
