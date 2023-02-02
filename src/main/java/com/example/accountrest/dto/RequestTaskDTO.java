package com.example.accountrest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestTaskDTO {
    private String title;
    private boolean completed;
}
