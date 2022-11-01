package com.fatec.sasbackend.util.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (Objects.isNull(localDate) ? null : Date.valueOf(localDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return (Objects.isNull(date) ? null : date.toLocalDate());
    }
}


