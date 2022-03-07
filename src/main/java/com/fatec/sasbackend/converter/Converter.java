package com.fatec.sasbackend.converter;

public interface Converter<E, D> {
    E fromDtoToEntity(E entity, D dto);
    D fromEntityToDto(D dto, E entity);
}
