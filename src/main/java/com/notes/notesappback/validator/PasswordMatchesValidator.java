package com.notes.notesappback.validator;

import com.notes.notesappback.dto.UserRegDto;
import com.notes.notesappback.validator.interfaces.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserRegDto user = (UserRegDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
