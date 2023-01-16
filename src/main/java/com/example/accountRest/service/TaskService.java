package com.example.accountRest.service;

import com.example.accountRest.dto.TaskDTO;

import com.example.accountRest.entity.UserEntity;
import com.example.accountRest.repository.TaskRepository;
import com.example.accountRest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;

    public TaskDTO createTask(TaskDTO taskDTO, String username) {
        UserEntity user = userRepo.findByUsernameOrEmail(username, username).orElse(null);
        if (user == null) {
            throw new ClassCastException(); //todo
        }
        taskDTO.setUser(user);
        taskRepo.save(taskDTO);
        return taskRepo.save(taskDTO);
    }
}
