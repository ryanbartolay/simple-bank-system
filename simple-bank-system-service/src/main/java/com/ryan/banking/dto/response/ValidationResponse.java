package com.ryan.banking.dto.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ValidationResponse {

    private List<FieldErrorMessage> errorMessages;
    private Integer errorCode;
    private Date timestamp;


    public ValidationResponse(){}
    public ValidationResponse(Integer errorCode){
        this.errorCode = errorCode;
        this.timestamp = new Date();
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    public Date getTimestamp() {
        return this.timestamp;
    }

}

