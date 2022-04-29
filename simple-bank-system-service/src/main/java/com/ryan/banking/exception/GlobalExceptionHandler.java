package com.ryan.banking.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ryan.banking.dto.response.FieldErrorMessage;
import com.ryan.banking.dto.response.ValidationResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ UserNotFoundException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handleUserNotFoundException(UserNotFoundException exception) {

        ValidationResponse body = ValidationResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessages(Arrays.asList(new FieldErrorMessage[] {
                        FieldErrorMessage
                            .builder()
                            .fieldName("user")
                            .message(exception.getMessage())
                            .build() }))
                .build();
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({ TransactionException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handleTransactionException(TransactionException exception) {

        ValidationResponse body = ValidationResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessages(Arrays.asList(new FieldErrorMessage[] {
                        FieldErrorMessage
                            .builder()
                            .fieldName("amount")
                            .message(exception.getMessage())
                            .build() }))
                .build();
        return ResponseEntity.badRequest().body(body);
    }
}
