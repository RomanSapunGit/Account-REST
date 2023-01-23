package com.example.account_rest.dto;





import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class SignInDTO  {
    private String username;
    private String password;
    private String role;

}
