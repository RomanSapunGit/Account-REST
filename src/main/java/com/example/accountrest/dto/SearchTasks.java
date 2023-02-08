package com.example.accountrest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchTasks {
    private String username;
    private String title;
}
