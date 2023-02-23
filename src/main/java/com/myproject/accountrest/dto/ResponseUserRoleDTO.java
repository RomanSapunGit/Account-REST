package com.myproject.accountrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserRoleDTO {
    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty
    private List<String> roles;
}
