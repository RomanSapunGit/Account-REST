package com.example.accountRest.service;

import com.example.accountRest.dto.TaskDTO;

import com.example.accountRest.entity.TaskEntity;
import com.example.accountRest.entity.UserEntity;
import com.example.accountRest.repository.TaskRepository;
import com.example.accountRest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


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
        TaskEntity task = new TaskEntity();
        task.setTask(taskDTO.getTask());
        task.setCompleted(taskDTO.isCompleted());
        task.setUser(user);
        return TaskDTO.totaskDTO( taskRepo.save(task));
    }
    public List<TaskDTO> showTasks( String username){
        UserEntity user = userRepo.findByUsernameOrEmail(username, username).orElse(null);
        if (user == null) {
            throw new ClassCastException(); //todo
        }
        TaskEntity task = taskRepo.findById(user.getId()).orElse(null);
        return taskRepo.getAllByUser(user);
    }
}
