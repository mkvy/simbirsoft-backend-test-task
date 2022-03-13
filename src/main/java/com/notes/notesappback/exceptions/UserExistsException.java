package com.notes.notesappback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exists")
public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
