package com.myproject.accountrest.service;

import com.myproject.accountrest.accountinterface.AccountConverter;
import com.myproject.accountrest.accountinterface.UserAuth;
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
    private UserAuth auth;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private AccountConverter converter;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws UserNotFoundException {
        UserEntity user = auth.findUserByAuth();
        TaskEntity task = new TaskEntity();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        return converter.convertToTaskDTO(Objects.requireNonNull(taskRepo.save(task)));
    }

    @Override
    public TaskDTO deleteTask(Long id) throws TaskNotFoundException, UserNotFoundException {
        TaskEntity deleteTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        UserEntity user = auth.findUserByAuth();
        List<TaskEntity> taskEntityList = user.getTask().stream().filter(deleteTask::equals).toList();
        if (taskEntityList.isEmpty()) {
            throw new TaskNotFoundException();
        }
        taskRepo.deleteById(id);
        return converter.convertToTaskDTO(deleteTask);
    }

    @Override
    public List<TaskDTO> updateTask(List<TaskDTO> updatedTasksDTO) {
        return updatedTasksDTO.stream().map(this::updateTask).collect(Collectors.toList());
    }

    @Override
    public TaskDTO searchTaskById(Long id) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        TaskEntity searchedTask = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        List<TaskDTO> taskList = userAuth.getTask()
                .stream()
                .filter(searchedTask::equals)
                .map(converter::convertToTaskDTO)
                .toList();
        if (taskList.isEmpty()) {
            if (searchedTask.isCompleted()) {
                return converter.convertToTaskDTO(searchedTask);
            } else {
                throw new TaskNotFoundException();
            }
        }
        return taskList.get(0);
    }

    @Override
    public List<ResponseTaskDTO> searchTasksByTitle(String searchTitle) throws UserNotFoundException, TaskNotFoundException {
        UserEntity userAuth = auth.findUserByAuth();
        List<TaskEntity> list = MatchTasksByTitle(searchTitle, userAuth.getTask());
        if (list.isEmpty()) {
            return handleListByUsername(list);
        }
        List<TaskEntity> taskEntities = taskRepo.getAllByCompleted(true);
        return handleListByUsername(MatchTasksByTitle(searchTitle, taskEntities));
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
                .map(user -> listOfTaskEntities.stream().map(converter::convertToListDTO).map
                                (taskDTOS -> new ResponseTaskDTO(user.getUsername(), taskDTOS))
                        .collect(Collectors.toList()))
                .toList());
        return newList.stream().findFirst().orElseThrow(TaskNotFoundException::new);
    }

    private TaskDTO updateTask(TaskDTO taskDTO) {
        TaskEntity task = taskRepo.findById(taskDTO.getId()).orElseThrow();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return converter.convertToTaskDTO(taskRepo.save(task));
    }
    private List<TaskEntity> MatchTasksByTitle(String searchTitle, List<TaskEntity> listToCheck){
        return listToCheck
                .stream()
                .filter(task -> task.getTitle().startsWith(searchTitle))
                .toList();
    }
}