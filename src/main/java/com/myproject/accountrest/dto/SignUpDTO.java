package com.myproject.accountrest.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpDTO {
    private String name;

    private String username;

    private String email;

    private String password;

}
