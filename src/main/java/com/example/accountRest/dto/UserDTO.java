package com.example.accountRest.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UserDTO {
    private String usernameOrEmail;
    private String password;
    private String role;
    private List<TaskDTO> tasks;
}
