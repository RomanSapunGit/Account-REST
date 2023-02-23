package com.myproject.accountrest.util.interfaces;

import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;


public interface TaskConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity, TaskDTO taskDTO);
}
