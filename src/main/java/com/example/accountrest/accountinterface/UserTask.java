package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.RequestTaskDTO;
import com.example.accountrest.dto.ResponseTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.exception.TaskNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;

public interface UserTask {

    TaskDTO createTask(RequestTaskDTO requestTaskDTO) throws UserNotFoundException;

    ResponseTaskDTO showTasks(String username) throws UserNotFoundException;

    TaskDTO delete(Long id) throws TaskNotFoundException;

    TaskDTO editTask(TaskDTO taskDTO) throws TaskNotFoundException;
}
