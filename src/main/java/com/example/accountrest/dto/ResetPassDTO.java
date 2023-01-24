package com.example.accountrest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPassDTO {
    private String password;
    private String token;
}
