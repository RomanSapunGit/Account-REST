package com.example.accountrest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPassDTO {
    @NotNull
    private String password;
    @NotNull
    private String matchPassword;
}
