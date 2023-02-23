package com.myproject.accountrest.util.implementation;

import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;
import org.springframework.stereotype.Component;


@Component
public class TaskConverter implements com.myproject.accountrest.util.interfaces.TaskConverter {

    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity, TaskDTO taskDTO) {
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTitle());
        taskDTO.setId(entity.getId());
        return taskDTO;
    }
}
