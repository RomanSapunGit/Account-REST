package com.myproject.accountrest.service.implementation;

import com.myproject.accountrest.util.interfaces.TaskConverter;
import com.myproject.accountrest.service.interfaces.UserAuthorization;
import com.myproject.accountrest.service.interfaces.UserTasks;
import com.myproject.accountrest.dto.ResponseTaskDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements UserTasks {
    private final UserAuthorization auth;
    private final TaskRepository taskRepo;
    private final TaskConverter taskConverter;

    @Autowired
    public TaskService(UserAuthorization auth, TaskRepository taskRepo, TaskConverter taskConverter) {
        this.auth = auth;
        this.taskRepo = taskRepo;
        this.taskConverter = taskConverter;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws UserNotFoundException {
        UserEntity user = auth.findUserByAuth();
        TaskEntity task = new TaskEntity();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        taskRepo.save(task);
        taskDTO.setId(task.getId());
        return taskDTO;
    }

    @Override
    public TaskDTO deleteTask(Long id) throws TaskNotFoundException, UserNotFoundException {
        TaskEntity deleteTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        UserEntity user = auth.findUserByAuth();
        if (!deleteTask.getUser().equals(user)) {
            throw new TaskNotFoundException();
        }
        taskRepo.deleteById(id);
        return taskConverter.convertToTaskDTO(deleteTask, new TaskDTO());
    }

    @Override
    public List<TaskDTO> updateTasks(List<TaskDTO> updatedTasksDTO) throws UserNotFoundException, TaskNotFoundException {
        UserEntity user = auth.findUserByAuth();
        List<TaskDTO> taskDTOS = updatedTasksDTO
                .stream()
                .filter(entity -> user.getTask()
                        .stream()
                        .map(taskEntity -> taskConverter.convertToTaskDTO(taskEntity, new TaskDTO()))
                        .anyMatch(task -> task.getId().equals(entity.getId())))
                .toList();
        if (!taskDTOS.isEmpty()) {
            return taskDTOS.stream().map(this::updateTask).collect(Collectors.toList());
        }
        throw new TaskNotFoundException();
    }

    @Override
    public TaskDTO findTaskById(Long id) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        TaskEntity searchedTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        if (searchedTask.getUser().equals(userAuth) || searchedTask.isCompleted()) {
            return taskConverter.convertToTaskDTO(searchedTask, new TaskDTO());
        }
        throw new TaskNotFoundException();
    }


    @Override
    public Set<ResponseTaskDTO> searchTasksByTitle(String searchTitle) throws UserNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        List<TaskEntity> list = matchTasksByTitle(searchTitle, userAuth.getTask());
        if (list.isEmpty()) {
            List<TaskEntity> taskEntities = taskRepo.getFirst20ByCompleted(true);
            return sortListOfTasksByUser(matchTasksByTitle(searchTitle, taskEntities));
        }
        return sortListOfTasksByUser(list);
    }

    private Set<ResponseTaskDTO> sortListOfTasksByUser(List<TaskEntity> list) {
        return list.stream()
                .map(TaskEntity::getUser)
                .distinct()
                .map(user -> new ResponseTaskDTO(user.getUsername(),
                        user.getTask()
                                .stream()
                                .filter(userTaskEntity -> list.stream().anyMatch(userTaskEntity::equals))
                                .map(userTaskEntity -> taskConverter.convertToTaskDTO(userTaskEntity, new TaskDTO()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toSet());
    }

    private TaskDTO updateTask(TaskDTO taskDTO) throws TaskNotFoundException {
        TaskEntity task = taskRepo.findById(taskDTO.getId()).orElseThrow(TaskNotFoundException::new);
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return taskConverter.convertToTaskDTO(taskRepo.save(task), taskDTO);
    }

    private List<TaskEntity> matchTasksByTitle(String searchTitle, List<TaskEntity> listToCheck) {
        return listToCheck
                .stream()
                .filter(task -> task.getTitle().startsWith(searchTitle))
                .toList();
    }
}