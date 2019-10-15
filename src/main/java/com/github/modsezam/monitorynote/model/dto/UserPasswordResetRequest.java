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
public class UserPasswordResetRequest {
    private Long accountId;

    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;
}
