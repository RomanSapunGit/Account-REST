package com.myproject.accountrest.util;

import com.myproject.accountrest.accountinterface.AccountConverter;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.repository.TaskRepository;
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
    public UserDTO convertToUserDTO(UserEntity entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(entity.getName());
        userDTO.setUsername(entity.getUsername());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }

    @Override
    public ResponseUserRoleDTO convertToResponseAuthorityDTO(UserEntity entity) {
        ResponseUserRoleDTO response = new ResponseUserRoleDTO();
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
