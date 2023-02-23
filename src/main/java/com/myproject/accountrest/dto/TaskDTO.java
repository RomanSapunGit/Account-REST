package com.myproject.accountrest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskDTO {
    @JsonProperty
    private Long id;
    @JsonProperty
    @NotEmpty
    private String title;
    @JsonProperty
    @NotEmpty
    private boolean completed;
}
