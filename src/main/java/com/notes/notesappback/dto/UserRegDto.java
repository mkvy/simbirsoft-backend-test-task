package com.notes.notesappback.dto;

import com.notes.notesappback.validator.interfaces.PasswordMatches;
import com.notes.notesappback.validator.interfaces.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserRegDto {
    @NotNull
    @NotEmpty
    @Email
    @ValidEmail
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
