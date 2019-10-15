package com.github.modsezam.monitorynote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;

    private String passwordConfirm;

    public boolean arePasswordsEqual() {
        return password.equals(passwordConfirm);
    }
}
