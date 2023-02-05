package com.example.accountrest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestTaskDTO {
    @NotNull
    private String title;
    @NotNull
    private boolean completed;
}
