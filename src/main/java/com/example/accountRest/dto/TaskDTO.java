package com.example.accountRest.dto;

import com.example.accountRest.entity.TaskEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class TaskDTO  {
    private String task;
    private boolean completed;
    public static TaskDTO totaskDTO(TaskEntity entity){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTask(entity.getTask());
        return taskDTO;
    }
}
