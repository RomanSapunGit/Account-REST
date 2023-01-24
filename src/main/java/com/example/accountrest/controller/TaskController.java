package com.example.accountrest.controller;

import com.example.accountrest.dto.ResponseTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")

public class TaskController {
    @Autowired
    private TaskService service;


    @PostMapping("/add-task")
    public ResponseEntity<TaskDTO> addNewTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(service.createTask(taskDTO), HttpStatus.OK);
    }

    @PutMapping("/show-tasks")
    public ResponseEntity<ResponseTaskDTO> showTasks(@RequestParam String username) {
        return new ResponseEntity<>(service.showTasks(username), HttpStatus.OK);
    }

    @PostMapping("/change-task-status")
    public ResponseEntity<TaskDTO> changeTaskStatus(@RequestParam Long id) {
        return new ResponseEntity<>(service.changeTaskStatus(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
