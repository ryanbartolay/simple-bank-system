package com.ryan.banking.controller.validator;

import java.util.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ryan.banking.exception.UserNotFoundException;
import com.ryan.banking.service.UserService;

@Component
public class UserValidator implements ConstraintValidator<UserConstraint, UUID> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserConstraint contactNumber) {
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext cxt) {
        try {
            return !ObjectUtils.isEmpty(userService.findUserById(id));
        } catch (UserNotFoundException e) {
            // We can suppress the thrown exception here, and use the constraints default
            // message.
            return false;
        }
    }

}
