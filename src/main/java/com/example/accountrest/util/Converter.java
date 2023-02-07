package com.example.accountrest.util;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.entity.RoleEntity;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


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
    @Override
    public UserDTO convertToUserDTO(UserEntity entity){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(entity.getName());
        userDTO.setUsername(entity.getUsername());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }
    @Override
    public ResponseAuthorityDTO convertToResponseAuthorityDTO(UserEntity entity){
        ResponseAuthorityDTO response = new ResponseAuthorityDTO();
        response.setName(entity.getName());
        response.setUsername(entity.getUsername());
        response.setRoles(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
        return response;
    }
}
