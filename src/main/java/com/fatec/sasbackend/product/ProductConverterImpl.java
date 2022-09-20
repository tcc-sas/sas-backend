package com.fatec.sasbackend.product;

import org.springframework.stereotype.Component;

@Component
public class ProductConverterImpl implements ProductConverter {
    @Override
    public Product fromDtoToEntity(Product entity, ProductDTO dto) {
        entity.setId(dto.getId());
        entity.setProductId(dto.getProductId());
        entity.setName(dto.getName());
        entity.setUnity(dto.getUnity());
        entity.setDescription(dto.getDescription());
        entity.setAvailableQuantity(dto.getAvailableQuantity());
        return entity;
    }

    @Override
    public ProductDTO fromEntityToDto(ProductDTO dto, Product entity) {
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setName(entity.getName());
        dto.setUnity(entity.getUnity());
        dto.setDescription(entity.getDescription());
        dto.setAvailableQuantity(entity.getAvailableQuantity());
        return dto;
    }
}
