package com.myproject.accountrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class UserDTO {
    @JsonProperty
    @NotEmpty
    private String name;
    @JsonProperty
    @NotEmpty
    private String username;
    @JsonProperty
    @NotEmpty
    private String email;
}
