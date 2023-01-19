package com.example.accountRest.service;

import com.example.accountRest.dto.RequestDTO;
import com.example.accountRest.dto.ResponseDTO;
import com.example.accountRest.dto.TaskDTO;
import com.example.accountRest.entity.TaskEntity;
import com.example.accountRest.entity.UserEntity;
import com.example.accountRest.exception.UserNotFoundException;
import com.example.accountRest.repository.TaskRepository;
import com.example.accountRest.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

    @SneakyThrows
    public TaskDTO createTask(RequestDTO requestDTO) {
        UserEntity user = userRepo.findByUsername(requestDTO.getUsername()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        TaskEntity task = new TaskEntity();
        task.setTask(requestDTO.getTitle());
        task.setCompleted(requestDTO.isCompleted());
        task.setUser(user);
        return TaskDTO.toTaskDTO(Objects.requireNonNull(taskRepo.save(task)));
    }

    @SneakyThrows
    public ResponseDTO showTasks(String username) {
        UserEntity user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        List<TaskEntity> list = taskRepo.getAllByUser(user);
        ResponseDTO responseDTO = new ResponseDTO();
        if (matchingUsername(user, username)) {
            responseDTO.setUsername(user.getUsername());
            responseDTO.setTasksList(list.stream().map(TaskDTO::toTaskDTO).collect(Collectors.toList()));
            return responseDTO;
        } else {
            responseDTO.setUsername(user.getUsername());
            return responseDTO;
        }
    }

    public boolean matchingUsername(UserEntity user, String username) {
        return user.getUsername().equals(username);
    }

    public String delete(Long id) {
        taskRepo.deleteById(id);
        return "Your task successfully deleted";
    }
}
