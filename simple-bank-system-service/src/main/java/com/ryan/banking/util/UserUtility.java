package com.ryan.banking.util;

import com.ryan.banking.controller.dto.UserDto;
import com.ryan.banking.model.User;

public class UserUtility {

    public static User toUser(UserDto userDto) {
        return User.builder().build();
    }
    
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }
}
