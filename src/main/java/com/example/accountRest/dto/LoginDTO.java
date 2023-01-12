package com.example.accountRest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
