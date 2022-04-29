package com.ryan.banking.model.converter;

import javax.money.CurrencyUnit;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

import com.ryan.banking.util.CurrencyUtility;

@Component
@Converter(autoApply = true)
public class CurrencyUnitConverter implements AttributeConverter<CurrencyUnit, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyUnit attribute) {
        return attribute.getCurrencyCode();
    }

    @Override
    public CurrencyUnit convertToEntityAttribute(String dbData) {
        return CurrencyUtility.toCurrencyUnit(dbData);
    }

}
