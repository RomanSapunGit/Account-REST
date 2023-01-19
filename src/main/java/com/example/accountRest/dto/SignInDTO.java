package com.example.accountRest.dto;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Data
public class SignInDTO {
    private String username;
    private String password;
    private String role;

}
