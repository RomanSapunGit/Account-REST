package com.example.account_rest.utility;

import com.example.account_rest.account_interface.AccountConverter;
import com.example.account_rest.dto.TaskDTO;
import com.example.account_rest.entity.TaskEntity;
import org.springframework.stereotype.Component;


@Component
public class Converter implements AccountConverter {
    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTask());
        return taskDTO;
    }
}
