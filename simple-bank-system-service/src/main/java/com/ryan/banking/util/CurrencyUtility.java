package com.ryan.banking.util;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.springframework.util.StringUtils;

public class CurrencyUtility {

    public static CurrencyUnit toCurrencyUnit(String currency) {
        return StringUtils.hasLength(currency) ? Monetary.getCurrency(currency) : null;
    }
 }
