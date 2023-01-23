package com.example.account_rest.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
@Setter
public class RequestTaskDTO {
    private String username;
    private String title;
    private boolean completed;
}
