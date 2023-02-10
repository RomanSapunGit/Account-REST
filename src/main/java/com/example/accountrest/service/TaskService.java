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


import java.util.*;
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
    public List<ResponseTaskDTO> searchTasks(String searchTitle) throws UserNotFoundException {
        UserEntity userAuth = findUserByAuth();
        List<TaskEntity> list;
        list = (userAuth.getTask()
                .stream()
                .filter(task -> task.getTitle().startsWith(searchTitle))
                .toList());
        if (!list.isEmpty()) {
            return putValuesInList(list);
        }
        List<TaskEntity> taskEntities = taskRepo.getAllByCompleted(true);
        List<TaskEntity> newList = taskEntities
                .stream()
                .filter(task -> task.getTitle().startsWith(searchTitle))
                .toList();
        return putValuesInList(newList);
    }

    private List<ResponseTaskDTO> putValuesInList(List<TaskEntity> list) {
        HashSet<List<TaskEntity>> sortedSet = new HashSet<>();
        List<List<TaskEntity>> listOfTaskEntities = new ArrayList<>(list.stream()
                .map(userRepo::getByTask)
                .map(user -> list.stream()
                        .map(task -> taskRepo.getTaskEntityByUserAndId(user, task.getId())).filter(Objects::nonNull).toList())
                .toList());
        listOfTaskEntities.removeIf(taskList -> !sortedSet.add(taskList));
        List<List<ResponseTaskDTO>> newList = new ArrayList<>(list.stream()
                .map(userRepo::getByTask)
                .distinct()
                .map(user -> listOfTaskEntities.stream().map(converter::convertToListDTO).map
                        (taskDTOS -> new ResponseTaskDTO(user.getUsername(), taskDTOS)).collect(Collectors.toList()))
                .toList());
        return newList.stream().findFirst().orElseThrow();
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