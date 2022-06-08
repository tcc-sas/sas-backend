package com.fatec.sasbackend.converter;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return (Objects.isNull(localDateTime) ? null : Timestamp.valueOf(localDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return (Objects.isNull(timestamp) ? null : timestamp.toLocalDateTime());
    }
}


