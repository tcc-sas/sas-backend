package com.fatec.sasbackend.stock;

import org.springframework.stereotype.Component;

@Component
public class StockConverterImpl implements StockConverter {
    @Override
    public Stock fromDtoToEntity(Stock entity, StockDTO dto) {
        entity.setId(dto.getId());
        return entity;
    }

    @Override
    public StockDTO fromEntityToDto(StockDTO dto, Stock entity) {
        dto.setId(entity.getId());
        return dto;
    }
}
