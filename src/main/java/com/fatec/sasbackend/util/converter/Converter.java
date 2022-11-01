package com.fatec.sasbackend.util.converter;

public interface Converter<E, D> {
    E fromDtoToEntity(E entity, D dto);
    D fromEntityToDto(D dto, E entity);
}
