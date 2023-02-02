package com.example.accountrest.controller;

import com.example.accountrest.accountinterface.UserTask;
import com.example.accountrest.dto.RequestTaskDTO;
import com.example.accountrest.dto.TaskDTO;
import com.example.accountrest.exception.TaskNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")

public class TaskController {
    @Autowired
    private UserTask userTask;


    @PostMapping("/add-task")
    public ResponseEntity<?> addNewTask(@RequestBody RequestTaskDTO requestTaskDTO) {
        try {
            return new ResponseEntity<>(userTask.createTask(requestTaskDTO), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/show-tasks")
    public ResponseEntity<?> showTasks(@RequestBody String username) {
        try {
            return new ResponseEntity<>(userTask.showTasks(username), HttpStatus.FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit-task")
    public ResponseEntity<?> editTask(@RequestBody TaskDTO taskDTO) {
        try {
            return new ResponseEntity<>(userTask.editTask(taskDTO), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(userTask.delete(id), HttpStatus.OK);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }
}
