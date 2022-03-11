package com.notes.notesappback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class UserDto {
    @NotNull
    @NotEmpty
    @Email
    private String username;
    @NotNull
    @NotEmpty
    private String password;
}
