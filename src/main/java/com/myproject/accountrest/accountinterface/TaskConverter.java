package com.myproject.accountrest.accountinterface;

import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;

import java.util.List;

public interface TaskConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity, TaskDTO taskDTO);
    List<TaskDTO> convertToListDTO(List<TaskEntity> list);
}
