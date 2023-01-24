package com.example.accountrest.util;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.entity.TaskEntity;
import org.springframework.stereotype.Component;


@Component
public class Converter implements AccountConverter {
    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTask());
        taskDTO.setId(entity.getId());
        return taskDTO;
    }
}
