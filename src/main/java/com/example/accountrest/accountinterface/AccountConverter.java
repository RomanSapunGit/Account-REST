package com.example.accountrest.accountinterface;


import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.entity.TaskEntity;


public interface AccountConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity);
}
