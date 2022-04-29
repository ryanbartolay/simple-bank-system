package com.ryan.banking.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ryan.banking.model.enums.AccountType;

@Component
@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    @Override
    public String convertToDatabaseColumn(AccountType attribute) {
        return !ObjectUtils.isEmpty(attribute) ? attribute.name() : null;
    }

    @Override
    public AccountType convertToEntityAttribute(String dbData) {
        return StringUtils.hasLength(dbData) ? AccountType.valueOf(dbData) : null;
    }

}
