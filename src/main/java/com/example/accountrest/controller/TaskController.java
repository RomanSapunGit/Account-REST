package com.example.accountrest.controller;

import com.example.accountrest.dto.ResponseTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.exception.TaskNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
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
    public ResponseEntity<?> addNewTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(service.createTask(taskDTO), HttpStatus.OK);
    }

    @GetMapping("/show-tasks")
    public ResponseEntity<?> showTasks(@RequestParam String username) {
        try {
            ResponseTaskDTO response = service.showTasks(username);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-task-status")
    public ResponseEntity<?> changeTaskStatus(@RequestParam Long id) {
        try {
            TaskDTO response = service.changeTaskStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.BAD_REQUEST);
        }
    }
}
