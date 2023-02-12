package com.myproject.accountrest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserRoleDTO {
    private String username;
    private String name;
    private List<String> roles;
}
