package com.example.accountRest.controller;

import com.example.accountRest.dto.TaskDTO;

import com.example.accountRest.entity.TaskEntity;
import com.example.accountRest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")

public class UserTaskController {
    @Autowired
    private TaskService service;

    @PostMapping("/add_task")
    public ResponseEntity<TaskDTO> addNewTask(@RequestBody TaskDTO taskDTO, @RequestParam String username) {
        return new ResponseEntity<>(service.createTask(taskDTO, username), HttpStatus.OK);
    }
    @PutMapping("/show")
    public ResponseEntity<List<TaskDTO>> showTasks(@RequestParam String username){
        return new ResponseEntity<>(service.showTasks(username), HttpStatus.OK);
    }//todo
}
