package com.ryan.banking.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.ryan.banking.dto.response.FieldErrorMessage;
import com.ryan.banking.dto.response.ValidationResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ RestUserNotFoundException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handleUserNotFoundException(RestUserNotFoundException exception) {

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

    @ExceptionHandler({ RestTransactionException.class })
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationResponse> handleTransactionException(Exception exception) {

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

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("error/404");
        return mv;
    }
}
