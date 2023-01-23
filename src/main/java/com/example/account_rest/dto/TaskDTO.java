package com.example.account_rest.dto;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@Getter
@Setter
public class TaskDTO  {
    private String title;
    private boolean completed;
}
