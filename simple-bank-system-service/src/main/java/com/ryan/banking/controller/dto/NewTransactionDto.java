package com.ryan.banking.controller.dto;

import java.util.UUID;

import org.joda.time.DateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewTransactionDto {

    private UUID id;
    private DateTime txDate;
}
