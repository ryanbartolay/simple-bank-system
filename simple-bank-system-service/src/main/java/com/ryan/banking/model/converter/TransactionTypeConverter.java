package com.ryan.banking.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ryan.banking.model.enums.TransactionType;

@Component
@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

    @Override
    public String convertToDatabaseColumn(TransactionType attribute) {
        return !ObjectUtils.isEmpty(attribute) ? attribute.name() : null;
    }

    @Override
    public TransactionType convertToEntityAttribute(String dbData) {
        return StringUtils.hasLength(dbData) ? TransactionType.valueOf(dbData) : null;
    }

}
