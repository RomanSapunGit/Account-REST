package com.example.accountrest.dto;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;

}
