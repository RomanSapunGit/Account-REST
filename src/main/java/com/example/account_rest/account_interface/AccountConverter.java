package com.example.account_rest.account_interface;


import com.example.account_rest.dto.TaskDTO;
import com.example.account_rest.entity.TaskEntity;


public interface AccountConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity);
}
