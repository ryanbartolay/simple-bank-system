package com.ryan.banking.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class FieldErrorMessage {

    @JsonProperty("fieldName")
    private String fieldName;

    private String message;

}