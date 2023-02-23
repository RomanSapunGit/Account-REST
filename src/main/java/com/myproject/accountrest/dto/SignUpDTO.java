package com.myproject.accountrest.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpDTO {
    @NotEmpty
    private String name;
    @NotEmpty

    private String username;
    @NotEmpty

    private String email;
    @NotEmpty

    private String password;

}
