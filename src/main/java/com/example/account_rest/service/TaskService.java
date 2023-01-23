package com.example.account_rest.service;

import com.example.account_rest.account_interface.AccountConverter;
import com.example.account_rest.dto.RequestTaskDTO;
import com.example.account_rest.dto.ResponseTaskDTO;
import com.example.account_rest.dto.TaskDTO;
import com.example.account_rest.entity.TaskEntity;
import com.example.account_rest.entity.UserEntity;
import com.example.account_rest.exception.UserNotFoundException;
import com.example.account_rest.repository.TaskRepository;
import com.example.account_rest.repository.UserRepository;
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
    private UserEntity user;
    @Autowired
    private AccountConverter converter;
    @Autowired
    private TaskEntity task;



    @SneakyThrows
    public TaskDTO createTask(RequestTaskDTO requestTaskDTO) {
        user = userRepo.findByUsername(requestTaskDTO.getUsername()).orElseThrow();
        task.setTask(requestTaskDTO.getTitle());
        task.setCompleted(requestTaskDTO.isCompleted());
        task.setUser(user);
        return converter.convertToTaskDTO(Objects.requireNonNull(taskRepo.save(task)));
    }

    @SneakyThrows
    public ResponseTaskDTO showTasks(String username) {
       user = findUserByAuth();
        List<TaskEntity> list = taskRepo.getAllByUser(user);
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO();
        if (matchByUsername(user.getUsername(), username)) {
            responseTaskDTO.setUsername(user.getUsername());
            responseTaskDTO.setTasksList(list.stream().map(converter::convertToTaskDTO).collect(Collectors.toList()));
        } else {
            user =  userRepo.findByUsername(username).orElse(null);
            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            responseTaskDTO.setUsername(user.getUsername());
        }
        return responseTaskDTO;
    }

    public String delete(Long id) {
        taskRepo.deleteById(id);
        return "Your task successfully deleted";
    }

    private boolean matchByUsername(String user, String username) {
        return user.equals(username);
    }
    private UserEntity findUserByAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return user;
    }
}
