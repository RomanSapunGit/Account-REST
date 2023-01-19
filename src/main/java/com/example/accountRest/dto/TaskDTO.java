package com.example.accountRest.dto;

import com.example.accountRest.entity.TaskEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class TaskDTO  {
    private String title;
    private boolean completed;
    public static TaskDTO toTaskDTO(TaskEntity entity){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTask());
        return taskDTO;
    }
}
