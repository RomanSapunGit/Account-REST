package com.example.accountrest.util;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Converter implements AccountConverter {
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTitle());
        taskDTO.setId(entity.getId());
        return taskDTO;
    }

}
