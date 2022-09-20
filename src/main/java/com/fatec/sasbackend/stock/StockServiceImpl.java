package com.fatec.sasbackend.stock;

import com.fatec.sasbackend.cras.Cras;
import com.fatec.sasbackend.cras.CrasConverter;
import com.fatec.sasbackend.cras.CrasDTO;
import com.fatec.sasbackend.cras.CrasRepository;
import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import com.fatec.sasbackend.product.Product;
import com.fatec.sasbackend.product.ProductConverter;
import com.fatec.sasbackend.product.ProductDTO;
import com.fatec.sasbackend.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockConverter stockConverter;
    private final ProductRepository productsRepository;
    private final ProductConverter productConverter;
    private final CrasRepository crasRepository;
    private final CrasConverter crasConverter;


    @Override
    public Page<StockDTO> findAllStock(Pageable pageable) {
        return stockRepository.findAll(pageable)
                .map(stock -> stockConverter.fromEntityToDto(new StockDTO(), stock));
    }

    @Override
    public Page<StockDTO> findPagedStockByFilter(Pageable pageable, Long productId, Long crasId) {
        return stockRepository.findPagedStockByFilter(
                        productId,
                        crasId,
                        pageable)
                .map(stock -> stockConverter.fromEntityToDto(new StockDTO(), stock));
    }

    @Override
    @Transactional
    public List<Stock> saveInStock(List<StockDTO> dtoList) {

        List<Stock> stockList = dtoList.stream()
                .map(dto -> stockConverter.fromDtoToEntity(new Stock(), dto))
                .map(this::fetchStockObjects)
                .toList();

        subtractFromProducts(stockList);

        for (Stock stock: stockList) {
            Stock stockToBeUpdated = stockRepository
                    .checkIfProductIsOnStockByNameAndCras(stock.getProduct().getId(), stock.getCras().getId());

            if(Objects.isNull(stockToBeUpdated)){
                stockRepository.save(stock);
            } else {
                stockToBeUpdated.setProductQuantity(
                        stockToBeUpdated.getProductQuantity() + stock.getProductQuantity()
                );
            }
        }

        return stockList;
    }

    private void subtractFromProducts(List<Stock> stockList) {
        for (Stock stock : stockList) {
            Product product = productsRepository.findById(stock.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Product Not Found"));

            if (product.getAvailableQuantity() - stock.getProductQuantity() >= 0) {
                product.setAvailableQuantity(
                        product.getAvailableQuantity() - stock.getProductQuantity()
                );
            }
        }
    }


    private Stock fetchStockObjects(Stock stock) {

        if (Objects.nonNull(stock.getProduct().getId())) {
            stock.setProduct(
                    productsRepository.findById(stock.getProduct().getId())
                            .orElseThrow(() -> new NotFoundException("Product Not Found!"))
            );
        }


        if (Objects.nonNull(stock.getCras().getId())) {
            stock.setCras(
                    crasRepository.findById(stock.getCras().getId())
                            .orElseThrow(() -> new NotFoundException("Cras Not Found!"))
            );
        }

        return stock;
    }

    @Override
    public StockRegistrationOptionsDTO findRegistrationOptions() {

        List<StockProductsRegistrationDTO> productsRegistrationDTOList = productsRepository
                .findAllProductsWithAvailableQuantity()
                .stream()
                .map(product -> StockProductsRegistrationDTO.builder()
                        .id(product.getId())
                        .name(product.getName() + " - " + product.getUnity())
                        .quantity(product.getAvailableQuantity())
                        .build()
                ).toList();

        List<CrasDTO> crasDTOList = crasRepository.findAll()
                .stream()
                .map(cras -> crasConverter.fromEntityToDto(new CrasDTO(), cras))
                .toList();


        return StockRegistrationOptionsDTO.builder()
                .products(productsRegistrationDTOList)
                .cras(crasDTOList)
                .build();

    }

    @Override
    public StockSelectOptions findSelectOptions() {
        List<ProductDTO> productDTOS = productsRepository.findAll()
                .stream()
                .map(product -> productConverter.fromEntityToDto(new ProductDTO(), product))
                .sorted(Comparator.comparing(ProductDTO::getName))
                .toList();

        List<CrasDTO> crasDTOS = crasRepository.findAll()
                .stream()
                .map(cras -> crasConverter.fromEntityToDto(new CrasDTO(), cras))
                .toList();

        return StockSelectOptions.builder()
                .product(productDTOS)
                .cras(crasDTOS)
                .build();

    }
}
