package com.myproject.accountrest.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeUserRoleDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String action;
    @NotEmpty
    private String role;
}
