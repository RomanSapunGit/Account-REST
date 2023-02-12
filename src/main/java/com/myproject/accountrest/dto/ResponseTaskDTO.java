package com.myproject.accountrest.dto;



import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
public class ResponseTaskDTO {
    private String username;
    List<TaskDTO> tasksList;

    public ResponseTaskDTO(String username, List<TaskDTO> tasksList) {
        this.username = username;
        this.tasksList = tasksList;
    }
}
