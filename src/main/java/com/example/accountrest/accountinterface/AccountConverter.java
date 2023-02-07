package com.example.accountrest.accountinterface;


import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;


public interface AccountConverter {
    TaskDTO convertToTaskDTO(TaskEntity entity);

    UserDTO convertToUserDTO(UserEntity entity);

    ResponseAuthorityDTO convertToResponseAuthorityDTO(UserEntity entity);

}
