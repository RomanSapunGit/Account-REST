package com.myproject.accountrest.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
public class ResponseTaskDTO {
    @JsonProperty
    private String username;
    @JsonProperty
    List<TaskDTO> tasksList;

    public ResponseTaskDTO(String username, List<TaskDTO> tasksList) {
        this.username = username;
        this.tasksList = tasksList;
    }
}
