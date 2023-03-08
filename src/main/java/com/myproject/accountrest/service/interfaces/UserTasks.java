package com.myproject.accountrest.service.interfaces;

import com.myproject.accountrest.dto.ResponseTaskDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserTasks {

    TaskDTO createTask(TaskDTO taskDTO) throws UserNotFoundException;

    TaskDTO deleteTask(Long id) throws TaskNotFoundException, UserNotFoundException;

    List<TaskDTO> updateTasks(List<TaskDTO> updatedTasksDTO) throws UserNotFoundException, TaskNotFoundException;

    TaskDTO findTaskById(Long id) throws UserNotFoundException, TaskNotFoundException;

    Set<ResponseTaskDTO> searchTasksByTitle(String searchTitle) throws UserNotFoundException, TaskNotFoundException;

}
