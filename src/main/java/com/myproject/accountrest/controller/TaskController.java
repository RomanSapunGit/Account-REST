package com.myproject.accountrest.controller;

import com.myproject.accountrest.service.interfaces.UserTasks;
import com.myproject.accountrest.dto.ResponseTaskDTO;
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
    private final UserTasks userTasks;

    @Autowired
    public TaskController(UserTasks userTasks) {
        this.userTasks = userTasks;
    }


    @PostMapping("/add")
    public ResponseEntity<TaskDTO> addNewTask(@RequestBody TaskDTO taskDTO) throws UserNotFoundException {
        return new ResponseEntity<>(userTasks.createTask(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping("/find")
    public ResponseEntity<TaskDTO> findTaskById(@RequestParam Long id) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTasks.searchTaskById(id), HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseTaskDTO>> searchTasks(@RequestParam String title) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTasks.searchTasksByTitle(title), HttpStatus.FOUND);
    }

    @PostMapping("/update")
    public ResponseEntity<List<TaskDTO>> updateTask(@RequestBody List<TaskDTO> taskDTO) throws UserNotFoundException, TaskNotFoundException {
        return new ResponseEntity<>(userTasks.updateTasks(taskDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TaskDTO> deleteTask(@RequestParam Long id) throws TaskNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(userTasks.deleteTask(id), HttpStatus.OK);
    }
}