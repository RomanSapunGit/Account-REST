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

import java.util.List;


@RestController
@RequestMapping("/api/tasks")

public class TaskController {
    @Autowired
    private UserTask userTask;


    @PostMapping("/add-task")
    public ResponseEntity<?> addNewTask(@RequestBody RequestTaskDTO requestTaskDTO) throws UserNotFoundException {
        return new ResponseEntity<>(userTask.createTask(requestTaskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/show-tasks")
    public ResponseEntity<?> showTasks(@RequestParam String username) throws UserNotFoundException {
        return new ResponseEntity<>(userTask.showTasks(username), HttpStatus.FOUND);
    }

    @GetMapping("/find-task")
    public ResponseEntity<?> findTaskById(@RequestParam Long id) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTask.searchTaskById(id), HttpStatus.FOUND);
    }

    @GetMapping("/search-tasks")
    public ResponseEntity<?> searchTasks(@RequestParam String title) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTask.searchTasks( title), HttpStatus.FOUND);
    }

    @PostMapping("/update-task")
    public ResponseEntity<?> updateTask(@RequestBody List<TaskDTO> taskDTO) {
        return new ResponseEntity<>(userTask.updateTask(taskDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long id) throws TaskNotFoundException {
        return new ResponseEntity<>(userTask.delete(id), HttpStatus.OK);
    }
}