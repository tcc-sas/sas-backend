package com.fatec.sasbackend.product;

import com.fatec.sasbackend.exception.AlreadyExistsException;
import com.fatec.sasbackend.exception.BadRequestException;
import com.fatec.sasbackend.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

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
                .orElseThrow(() -> new NotFoundException("Product not found!"));
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO dto) {
        if(Objects.isNull(dto.getId())){
            throw new BadRequestException("Product ID is null");
        }

        if(productRepository.checkIfNameIsAlreadyTakenToUpdate(dto.getId(), dto.getName())){
            throw new AlreadyExistsException("Product with name " + dto.getName() + " is already taken");
        }

        Product entity = productRepository.findById(dto.getId())
                .map(product -> productConverter.fromDtoToEntity(product, dto))
                .orElseThrow(( ) -> new NotFoundException("Product not found"));

        return productConverter.fromEntityToDto(dto, entity);
    }

    @Override
    public ProductDTO registerProduct(ProductDTO dto) {
        if(productRepository.checkIfNameIsAlreadyTaken(dto.getName())){
            throw new AlreadyExistsException("Username Already taken");
        }

        Product entity = productRepository.findById(dto.getId())
                .map(product -> productConverter.fromDtoToEntity(product, dto))
                .orElseThrow(( ) -> new NotFoundException("Product not found"));

        productRepository.save(entity);
        return dto;
    }
}
