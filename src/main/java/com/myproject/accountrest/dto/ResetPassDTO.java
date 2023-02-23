package com.myproject.accountrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResetPassDTO {
    @JsonProperty
    @NotEmpty
    private String password;
    @JsonProperty
    @NotEmpty
    private String matchPassword;
}
