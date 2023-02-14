package com.myproject.accountrest.service;

import com.myproject.accountrest.accountinterface.TaskConverter;
import com.myproject.accountrest.accountinterface.UserConverter;
import com.myproject.accountrest.accountinterface.UserAuthorization;
import com.myproject.accountrest.accountinterface.UserTasks;
import com.myproject.accountrest.dto.ResponseTaskDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.repository.TaskRepository;
import com.myproject.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService implements UserTasks {
    @Autowired
    private UserAuthorization auth;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private TaskConverter taskConverter;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws UserNotFoundException {
        UserEntity user = auth.findUserByAuth();
        TaskEntity task = new TaskEntity();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        taskRepo.save(task);
        return taskDTO;
    }

    @Override
    public TaskDTO deleteTask(Long id) throws TaskNotFoundException, UserNotFoundException {
        TaskEntity deleteTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        UserEntity user = auth.findUserByAuth();
        if (deleteTask.getUser().equals(user)) {
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
    public TaskDTO searchTaskById(Long id) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        TaskEntity searchedTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        if (searchedTask.getUser().equals(userAuth)) {
            return taskConverter.convertToTaskDTO(searchedTask, new TaskDTO());
        } else {
            if (searchedTask.isCompleted()) {
                return taskConverter.convertToTaskDTO(searchedTask, new TaskDTO());
            } else {
                throw new TaskNotFoundException();
            }
        }
    }

    @Override
    public List<ResponseTaskDTO> searchTasksByTitle(String searchTitle) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        List<TaskEntity> list = matchTasksByTitle(searchTitle, userAuth.getTask());
        if (list.isEmpty()) {
            List<TaskEntity> taskEntities = taskRepo.getAllByCompleted(true);
            return handleListByUsername(matchTasksByTitle(searchTitle, taskEntities));
        }
        return handleListByUsername(list);
    }

    private List<ResponseTaskDTO> handleListByUsername(List<TaskEntity> list) throws TaskNotFoundException {
        HashSet<List<TaskEntity>> sortedSet = new HashSet<>();
        List<List<TaskEntity>> listOfTaskEntities = new ArrayList<>(list.stream()
                .map(userRepo::getByTask)
                .map(user -> list.stream()
                        .map(task -> taskRepo.getTaskEntityByUserAndId(user, task.getId()))
                        .filter(Objects::nonNull).toList())
                .toList());
        listOfTaskEntities.removeIf(taskList -> !sortedSet.add(taskList));
        List<List<ResponseTaskDTO>> newList = new ArrayList<>(list.stream()
                .map(userRepo::getByTask)
                .distinct()
                .map(user -> listOfTaskEntities.stream().map(taskConverter::convertToListDTO).map
                                (taskDTOS -> new ResponseTaskDTO(user.getUsername(), taskDTOS))
                        .collect(Collectors.toList()))
                .toList());
        return newList.stream().findFirst().orElseThrow(TaskNotFoundException::new);
    }

    private TaskDTO updateTask(TaskDTO taskDTO) {
        TaskEntity task = taskRepo.findById(taskDTO.getId()).orElseThrow();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return taskConverter.convertToTaskDTO(taskRepo.save(task), new TaskDTO());
    }

    private List<TaskEntity> matchTasksByTitle(String searchTitle, List<TaskEntity> listToCheck) {
        return listToCheck
                .stream()
                .filter(task -> task.getTitle().startsWith(searchTitle))
                .toList();
    }
}