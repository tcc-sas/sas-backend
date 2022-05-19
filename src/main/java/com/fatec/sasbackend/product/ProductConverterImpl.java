package com.fatec.sasbackend.product;

import org.springframework.stereotype.Component;

@Component
public class ProductConverterImpl implements ProductConverter {
    @Override
    public Product fromDtoToEntity(Product entity, ProductDTO dto) {
        entity.setId(dto.getId());
        entity.setProductId(dto.getProductId());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public ProductDTO fromEntityToDto(ProductDTO dto, Product entity) {
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setName(entity.getName());
        return dto;
    }
}
