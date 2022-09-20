package com.fatec.sasbackend.product;

import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Produto não encontrado!";

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(product -> productConverter.fromEntityToDto(new ProductDTO(), product));
    }

    @Override
    public Page<ProductDTO> findPagedProductsByFilter(Pageable pageable, String productId, String name) {
        return productRepository.findPagedProductsByFilter(
                        productId,
                        name.toLowerCase(),
                        pageable)
                .map(product -> productConverter.fromEntityToDto(new ProductDTO(), product));
    }

    @Override
    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> productConverter.fromEntityToDto(new ProductDTO(), product))
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new BadRequestException("Product ID is null");
        }

        if (Boolean.TRUE.equals(productRepository.checkIfNameIsAlreadyTakenToUpdate(dto.getId(), dto.getName()))) {
            throw new AlreadyExistsException("Produto " + dto.getName().toUpperCase() + " já cadastrado!");
        }

        Product entity = productRepository.findById(dto.getId())
                .map(product -> productConverter.fromDtoToEntity(product, dto))
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));

        return productConverter.fromEntityToDto(dto, entity);
    }

    @Override
    public ProductDTO registerProduct(ProductDTO dto) {
        if (Boolean.TRUE.equals(productRepository.checkIfNameIsAlreadyTaken(dto.getName()))) {
            throw new AlreadyExistsException("Produto " + dto.getName().toUpperCase() + " já cadastrado!");
        }

        Product entity = productConverter.fromDtoToEntity(new Product(), dto);

        productRepository.save(entity);
        return dto;
    }

    @Override
    public void deleteProduct(String id) {
        Product entity = productRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));

        if(entity.getAvailableQuantity() > 0) {
            throw new BadRequestException("Produtos com quantidades disponiveis não podem ser excluidos do estoque!");
        }

        productRepository.deleteById(Long.parseLong(id));
    }


    @Override
    public List<SimpleProductDTO> findProductsForBeneficiary() {
        return productRepository.findAll()
                .stream()
                .map(product -> SimpleProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .build()
                )
                .toList();
    }
}
