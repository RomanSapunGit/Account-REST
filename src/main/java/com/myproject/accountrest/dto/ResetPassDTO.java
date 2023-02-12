package com.myproject.accountrest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPassDTO {
    @NotEmpty
    private String password;
    @NotEmpty
    private String matchPassword;
}
