package com.example.accountrest.service;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.accountinterface.UserTask;
import com.example.accountrest.dto.RequestTaskDTO;
import com.example.accountrest.dto.ResponseTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.TaskNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.repository.TaskRepository;
import com.example.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService implements UserTask {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private AccountConverter converter;

    @Override
    public TaskDTO createTask(RequestTaskDTO requestTaskDTO) throws UserNotFoundException {
        UserEntity user = findUserByAuth();
        TaskEntity task = new TaskEntity();
        task.setTitle(requestTaskDTO.getTitle());
        task.setCompleted(requestTaskDTO.isCompleted());
        task.setUser(user);

        return converter.convertToTaskDTO(Objects.requireNonNull(taskRepo.save(task)));
    }

    @Override
    public ResponseTaskDTO showTasks(String username) throws UserNotFoundException {
        UserEntity user = findUserByAuth();
        List<TaskEntity> UserList = taskRepo.getAllByUser(user);
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO();
        responseTaskDTO.setUsername(user.getUsername());
        if (user.getUsername().equals(username)) {
            responseTaskDTO.setTasksList(UserList.stream()
                    .map(converter::convertToTaskDTO)
                    .collect(Collectors.toList()));
        } else {
            user = userRepo.findByUsername(username).orElseThrow(UserNotFoundException::new);
            List<TaskEntity> externalUserList = taskRepo.getAllByUser(user);
            responseTaskDTO.setUsername(user.getUsername());
            responseTaskDTO.setTasksList(externalUserList
                    .stream()
                    .filter(TaskEntity::isCompleted)
                    .map(converter::convertToTaskDTO)
                    .collect(Collectors.toList()));
        }
        return responseTaskDTO;
    }

    @Override
    public TaskDTO delete(Long id) throws TaskNotFoundException {
        TaskEntity deletedTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepo.deleteById(id);
        return converter.convertToTaskDTO(deletedTask);
    }

    @Override
    public List<TaskDTO> updateTask(List<TaskDTO> updatedTasksDTO) {
        return updatedTasksDTO.stream().map(this::updateTask).collect(Collectors.toList());
    }

    @Override
    public TaskDTO searchTaskById(Long id) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = findUserByAuth();
        TaskEntity searchedTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        List<TaskDTO> taskList = userAuth.getTask()
                .stream()
                .filter(searchedTask::equals)
                .map(converter::convertToTaskDTO)
                .toList();
        if (taskList.isEmpty()) {
            if (searchedTask.isCompleted()) {
                return converter.convertToTaskDTO(searchedTask);
            }
        }
        return taskList.get(0);
    }

    @Override
    public ResponseTaskDTO searchTasks( String searchTitle) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = findUserByAuth();
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO();
        List<TaskEntity> taskEntities;
            responseTaskDTO.setTasksList(userAuth.getTask()
                    .stream()
                    .filter(task -> task.getTitle().startsWith(searchTitle))
                    .map(converter::convertToTaskDTO)
                    .collect(Collectors.toList()));
            if(responseTaskDTO.getTasksList().isEmpty()){
                taskEntities = taskRepo.getAllByCompleted(true);
            responseTaskDTO.setTasksList(taskEntities
                    .stream()
                    .filter(task -> task.getTitle().startsWith(searchTitle))
                    .map(converter::convertToTaskDTO)
                    .collect(Collectors.toList()));
        }
            UserEntity user = userRepo.getByTask
                    (converter.convertToTaskEntity(responseTaskDTO.getTasksList().get(0))).orElseThrow(UserNotFoundException::new);
        responseTaskDTO.setUsername(user.getUsername());
        return responseTaskDTO;
    }

    private TaskDTO updateTask(TaskDTO taskDTO) {
        TaskEntity task = taskRepo.findById(taskDTO.getId()).orElseThrow();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return converter.convertToTaskDTO(taskRepo.save(task));
    }

    private UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}