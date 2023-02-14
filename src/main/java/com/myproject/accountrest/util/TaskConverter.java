package com.myproject.accountrest.util;

import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class TaskConverter implements com.myproject.accountrest.accountinterface.TaskConverter {
    @Override
    public List<TaskDTO> convertToListDTO(List<TaskEntity> list) {

        return list.stream()
                .map(entity -> convertToTaskDTO(entity,new TaskDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity, TaskDTO taskDTO) {
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTitle());
        taskDTO.setId(entity.getId());
        return taskDTO;
    }
}
