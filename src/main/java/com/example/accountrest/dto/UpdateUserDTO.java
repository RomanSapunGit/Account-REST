package com.example.accountrest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class UpdateUserDTO {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String email;
}
