package com.example.accountrest.service;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.dto.ResponseTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.TaskNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.repository.TaskRepository;
import com.example.accountrest.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private AccountConverter converter;


    public TaskDTO createTask(TaskDTO taskDTO) {
        UserEntity user = findUserByAuth();
        TaskEntity task = new TaskEntity();
        task.setTask(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        return converter.convertToTaskDTO(Objects.requireNonNull(taskRepo.save(task)));
    }

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


    public TaskDTO changeTaskStatus(Long id) throws TaskNotFoundException {
        TaskEntity task = taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        task.setCompleted(!task.isCompleted());
        return converter.convertToTaskDTO(task);
    }

    public String delete(Long id) throws TaskNotFoundException {
        taskRepo.findById(id).orElseThrow(TaskNotFoundException::new);
        taskRepo.deleteById(id);
        return "Your task successfully deleted";
    }

    @SneakyThrows
    private UserEntity findUserByAuth()  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}
