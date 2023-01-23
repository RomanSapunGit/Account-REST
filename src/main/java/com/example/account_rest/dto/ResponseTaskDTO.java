package com.example.account_rest.dto;



import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class ResponseTaskDTO {
    private String username;
    List<TaskDTO> tasksList;

}
