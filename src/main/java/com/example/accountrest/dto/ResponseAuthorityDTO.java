package com.example.accountrest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAuthorityDTO {
    private String username;
    private String name;
    private List<String> roles;
}
