package com.example.AccountRest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
