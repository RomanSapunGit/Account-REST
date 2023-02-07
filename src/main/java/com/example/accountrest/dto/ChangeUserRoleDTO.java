package com.example.accountrest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeUserRoleDTO {
    private String username;
    private String role;
}
