package com.ryan.banking.model.converter;

import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@Converter(autoApply = true)
public class DateTimeConverter implements AttributeConverter<DateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(DateTime attribute) {
        return !ObjectUtils.isEmpty(attribute) ? attribute.toDate() : null;
    }

    @Override
    public DateTime convertToEntityAttribute(Date dbData) {
        return !ObjectUtils.isEmpty(dbData) ? new DateTime(dbData) : null;
    }

}
