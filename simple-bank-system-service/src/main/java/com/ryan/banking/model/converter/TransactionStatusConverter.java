package com.ryan.banking.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ryan.banking.model.enums.TransactionStatus;

@Component
@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(TransactionStatus attribute) {
        return !ObjectUtils.isEmpty(attribute) ? attribute.name() : null;
    }

    @Override
    public TransactionStatus convertToEntityAttribute(String dbData) {
        return StringUtils.hasLength(dbData) ? TransactionStatus.valueOf(dbData) : null;
    }

}
