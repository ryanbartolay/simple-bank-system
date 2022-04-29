package com.ryan.banking.model.converter;

import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class MoneyAmountAttributeConverter implements AttributeConverter<MonetaryAmount, String> {

    private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

    @Override
    public String convertToDatabaseColumn(MonetaryAmount amount) {
        return amount == null ? null : amount.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String source) {
        return source == null ? null : Money.parse(source, FORMAT);
    }
}