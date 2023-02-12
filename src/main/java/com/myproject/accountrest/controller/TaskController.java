package com.myproject.accountrest.controller;

import com.myproject.accountrest.accountinterface.UserTasks;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")

public class TaskController {
    @Autowired
    private UserTasks userTasks;


    @PostMapping("/add-task")
    public ResponseEntity<?> addNewTask(@RequestBody TaskDTO taskDTO) throws UserNotFoundException {
        return new ResponseEntity<>(userTasks.createTask(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find-task")
    public ResponseEntity<?> findTaskById(@RequestParam Long id) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTasks.searchTaskById(id), HttpStatus.FOUND);
    }

    @GetMapping("/search-tasks")
    public ResponseEntity<?> searchTasks(@RequestParam String title) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTasks.searchTasksByTitle( title), HttpStatus.FOUND);
    }

    @PostMapping("/update-task")
    public ResponseEntity<?> updateTask(@RequestBody List<TaskDTO> taskDTO) {
        return new ResponseEntity<>(userTasks.updateTask(taskDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long id) throws TaskNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(userTasks.deleteTask(id), HttpStatus.OK);
    }
}