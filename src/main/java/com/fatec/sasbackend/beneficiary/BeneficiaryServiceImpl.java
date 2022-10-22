package com.fatec.sasbackend.beneficiary;

import com.fatec.sasbackend.beneficiaryrecords.BeneficiaryRecords;
import com.fatec.sasbackend.beneficiaryrecords.BeneficiaryRecordsRepository;
import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.cras.CrasConverter;
import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.cras.CrasRepository;
import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import com.fatec.sasbackend.product.Product;
import com.fatec.sasbackend.product.ProductRepository;
import com.fatec.sasbackend.product.SimpleProductDTO;
import com.fatec.sasbackend.stock.Stock;
import com.fatec.sasbackend.stock.StockRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@AllArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    public static final String BENEFICIARY_NOT_FOUND = "Beneficiário não encontrado!";
    public static final String CPF_ALREADY_TAKEN = "CPF já cadastrado";

    private final BeneficiaryRepository repository;
    private final BeneficiaryConverter converter;
    private final CrasRepository crasRepository;
    private final CrasConverter crasConverter;

    private final StockRepository stockRepository;

    private final ProductRepository productRepository;

    private final BeneficiaryProductsRepository beneficiaryProductsRepository;

    private final BeneficiaryRecordsRepository beneficiaryRecordsRepository;


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
    @Transactional
    public Boolean benefitBeneficiary(Long id) {

        Beneficiary beneficiary = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(BENEFICIARY_NOT_FOUND));

        List<BeneficiaryProducts> beneficiaryProductsList = beneficiaryProductsRepository.findAllByBeneficiaryId(beneficiary.getId());

        if(beneficiaryProductsList.isEmpty()){
           throw new BadRequestException("BENEFICIÁRIO NÃO CONTEM PRODUTOS VINCULADOS NO SISTEMA");
        }

        List<Long> productsIds = beneficiaryProductsList.stream()
                .map(x -> x.getProduct().getId())
                .toList();

        List<Stock> stockList = stockRepository.findAllByCrasIdAndProductIdIn(beneficiary.getCras().getId(), productsIds);

        BeneficiaryRecords beneficiaryRecord = beneficiaryRecordsRepository.getByYearAndMonth(
                beneficiary.getId(),
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue()
        );

        if(Objects.nonNull(beneficiaryRecord)){
            throw new BadRequestException(
                    "BENEFICIÁRIO JÁ CONTEMPLADO EM "+beneficiaryRecord.getBenefitDeliveryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );
        }

        checkIfProductIsAvailable(beneficiaryProductsList, stockList);
        checkIfProductQuantityIsAvailable(beneficiaryProductsList, stockList);
        subtractFromStock(beneficiaryProductsList, stockList);

        beneficiaryRecordsRepository.save(
                BeneficiaryRecords.builder()
                        .beneficiary(beneficiary)
                        .benefitDeliveryDate(LocalDate.now())
                        .build()
        );

        return true;
    }

    private void checkIfProductIsAvailable(List<BeneficiaryProducts> beneficiaryProductsList, List<Stock> stockList) {

        HashSet<Long> stockHash = new HashSet<>(stockList.stream()
                .map(x -> x.getProduct().getId())
                .toList());

        List<Long> productsIds = beneficiaryProductsList.stream()
                .map(x -> x.getProduct().getId())
                .toList();

        if(!stockHash.containsAll(productsIds)){

            List<Long> stockProductsIds = stockList.stream().
                    map(x -> x.getProduct().getId())
                    .toList();

            StringBuilder sb = new StringBuilder();
            sb.append("OS SEGUINTES PRODUTOS: </br>");

            beneficiaryProductsList.forEach(beneficiaryProducts -> {
                if(!stockProductsIds.contains(beneficiaryProducts.getProduct().getId())){
                    sb.append("<strong>")
                            .append(beneficiaryProducts.getProduct().getName())
                            .append("</strong>")
                            .append("</br>");

                }
            });

            sb.append("NÃO ESTÃO DISPONIVEIS NO ESTOQUE");
            throw new BadRequestException(sb.toString());
        }
    }

    private static void checkIfProductQuantityIsAvailable(List<BeneficiaryProducts> beneficiaryProductsList, List<Stock> stockList) {

        List<Product> productList = new ArrayList<>();

        for (BeneficiaryProducts beneficiaryProduct: beneficiaryProductsList) {
            for (Stock stock: stockList) {
                if(Objects.equals(beneficiaryProduct.getProduct().getId(), stock.getProduct().getId())
                        && stock.getProductQuantity() - beneficiaryProduct.getQuantity() < 0){
                    productList.add(stock.getProduct());
                }
            }
        }

        if(!productList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("OS SEGUINTES PRODUTOS: </br>");
            productList.forEach(product -> {
                sb.append("<strong>")
                  .append(product.getName())
                  .append("</strong>")
                  .append("</br>");
            });
            sb.append("ESTÃO EM FALTA NO ESTOQUE");
            throw new BadRequestException(sb.toString());
        }
    }

    private static void subtractFromStock(List<BeneficiaryProducts> beneficiaryProductsList, List<Stock> stockList) {
        for (BeneficiaryProducts beneficiaryProduct: beneficiaryProductsList) {
            for (Stock stock: stockList) {
                if(Objects.equals(beneficiaryProduct.getProduct().getId(), stock.getProduct().getId())
                        && stock.getProductQuantity() - beneficiaryProduct.getQuantity() >= 0){
                    stock.setProductQuantity(stock.getProductQuantity() - beneficiaryProduct.getQuantity());
                }
            }

        }
    }

    @Override
    public void deleteBeneficiary(String id) {
        Beneficiary beneficiary = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException(BENEFICIARY_NOT_FOUND));
        repository.deleteById(beneficiary.getId());
    }


}
