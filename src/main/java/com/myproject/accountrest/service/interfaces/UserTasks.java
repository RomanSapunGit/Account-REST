package com.myproject.accountrest.service.interfaces;

import com.myproject.accountrest.dto.ResponseTaskDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;

import java.util.List;

public interface UserTasks {

    TaskDTO createTask(TaskDTO taskDTO) throws UserNotFoundException;

    TaskDTO deleteTask(Long id) throws TaskNotFoundException, UserNotFoundException;

    List<TaskDTO> updateTasks(List<TaskDTO> updatedTasksDTO) throws UserNotFoundException, TaskNotFoundException;

    TaskDTO searchTaskById(Long id) throws UserNotFoundException, TaskNotFoundException;

    List<ResponseTaskDTO> searchTasksByTitle(String searchTitle) throws UserNotFoundException, TaskNotFoundException;

}
