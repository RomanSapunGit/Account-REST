package com.myproject.accountrest.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class SignInDTO  {
    @NotEmpty
    @JsonProperty
    private String username;
    @NotEmpty
    @JsonProperty
    private String password;
    @NotEmpty
    @JsonProperty
    private String role;

}
