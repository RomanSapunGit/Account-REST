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
        if (user.getUsername().equals(username)) {
            responseTaskDTO.setUsername(user.getUsername());
            responseTaskDTO.setTasksList(UserList.stream()
                    .map(converter::convertToTaskDTO)
                    .collect(Collectors.toList()));
        } else {
            user = userRepo.findByUsername(username).orElseThrow(UserNotFoundException::new);
            List<TaskEntity> externalUserList = taskRepo.getAllByUser(user);
            List<TaskEntity> completedTasksList = externalUserList.stream().filter(TaskEntity::isCompleted).toList();
            responseTaskDTO.setUsername(user.getUsername());
            responseTaskDTO.setTasksList(completedTasksList.stream().map(converter::convertToTaskDTO).collect(Collectors.toList()));
        }
        return responseTaskDTO;
    }



    @Override
    public String delete(Long id) throws TaskNotFoundException {
        taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepo.deleteById(id);
        return "Your task successfully deleted";
    }

    @Override
    public TaskDTO editTask(TaskDTO taskDTO) throws TaskNotFoundException {
        TaskEntity task = taskRepo.findById(taskDTO.getId()).orElseThrow(TaskNotFoundException::new);
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        return converter.convertToTaskDTO(taskRepo.save(task));
    }

    private UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}
