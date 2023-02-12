package com.myproject.accountrest.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private boolean completed;
}
