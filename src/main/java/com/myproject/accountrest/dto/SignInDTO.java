package com.myproject.accountrest.dto;



import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class SignInDTO  {
    private String username;
    private String password;
    private String role;

}
