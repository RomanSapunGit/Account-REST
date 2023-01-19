package com.example.accountRest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RequestDTO {
    private String username;
    private String title;
    private boolean completed;
}
