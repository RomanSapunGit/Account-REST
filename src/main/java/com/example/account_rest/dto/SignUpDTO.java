package com.example.account_rest.dto;



import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private String token;
}
