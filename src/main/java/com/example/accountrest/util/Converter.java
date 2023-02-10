package com.example.accountrest.util;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.entity.RoleEntity;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.List;

@Component
public class Converter implements AccountConverter {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDTO convertToTaskDTO(TaskEntity entity) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setCompleted(entity.isCompleted());
        taskDTO.setTitle(entity.getTitle());
        taskDTO.setId(entity.getId());
        return taskDTO;
    }

    @Override
    public TaskEntity convertToTaskEntity(TaskDTO dto) {
        TaskEntity task = taskRepository.findById(dto.getId()).orElseThrow();
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());
        task.setId(dto.getId());
        return task;
    }

    @Override
    public UserDTO convertToUserDTO(UserEntity entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(entity.getName());
        userDTO.setUsername(entity.getUsername());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }

    @Override
    public ResponseAuthorityDTO convertToResponseAuthorityDTO(UserEntity entity) {
        ResponseAuthorityDTO response = new ResponseAuthorityDTO();
        response.setName(entity.getName());
        response.setUsername(entity.getUsername());
        response.setRoles(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
        return response;
    }

    @Override
    public List<TaskDTO> convertToListDTO(List<TaskEntity> list) {
        List<TaskDTO> convertList;
       convertList = list.stream().map(this::convertToTaskDTO).collect(Collectors.toList());
       return convertList;
    }
}
