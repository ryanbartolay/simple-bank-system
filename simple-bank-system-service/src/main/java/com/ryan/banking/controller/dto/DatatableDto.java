package com.ryan.banking.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DatatableDto<T> {

    private T data;

}
