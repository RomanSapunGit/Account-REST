package com.example.accountrest.accountinterface;


import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;

import java.util.List;


public interface AccountConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity);

    TaskEntity convertToTaskEntity(TaskDTO dto);

    UserDTO convertToUserDTO(UserEntity entity);

    ResponseAuthorityDTO convertToResponseAuthorityDTO(UserEntity entity);

    List<TaskDTO> convertToListDTO(List<TaskEntity> list);
}
