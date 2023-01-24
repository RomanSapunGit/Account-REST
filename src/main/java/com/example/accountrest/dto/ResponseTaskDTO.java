package com.example.accountrest.dto;



import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
public class ResponseTaskDTO {
    private String username;
    List<TaskDTO> tasksList;

}
