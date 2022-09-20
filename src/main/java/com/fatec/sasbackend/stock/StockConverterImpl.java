package com.fatec.sasbackend.stock;

import org.springframework.stereotype.Component;

@Component
public class StockConverterImpl implements StockConverter {

    @Override
    public Stock fromDtoToEntity(Stock entity, StockDTO dto) {
        entity.setId(dto.getId());
        entity.setProduct(dto.getProduct());
        entity.setCras(dto.getCras());
        entity.setProductQuantity(dto.getProductQuantity());

        return entity;
    }

    @Override
    public StockDTO fromEntityToDto(StockDTO dto, Stock entity) {
        dto.setId(entity.getId());
        dto.setProduct(entity.getProduct());
        dto.setCras(entity.getCras());
        dto.setProductQuantity(entity.getProductQuantity());

        return dto;
    }
}
