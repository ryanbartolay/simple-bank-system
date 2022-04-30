package com.ryan.banking.controller.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private UUID id;
    private String email;
    private String firstname;
    private String lastname;

}
