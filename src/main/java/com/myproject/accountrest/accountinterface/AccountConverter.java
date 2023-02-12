package com.myproject.accountrest.accountinterface;


import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;

import java.util.List;


public interface AccountConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity);

    UserDTO convertToUserDTO(UserEntity entity);

    ResponseUserRoleDTO convertToResponseAuthorityDTO(UserEntity entity);

    List<TaskDTO> convertToListDTO(List<TaskEntity> list);
}
