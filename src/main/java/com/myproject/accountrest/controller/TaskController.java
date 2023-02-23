package com.myproject.accountrest.controller;

import com.myproject.accountrest.service.interfaces.UserTasks;
import com.myproject.accountrest.dto.ResponseTaskDTO;
import com.myproject.accountrest.dto.TaskDTO;
import com.myproject.accountrest.exception.TaskNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/tasks")

public class TaskController {
    private final UserTasks userTasks;

    @Autowired
    public TaskController(UserTasks userTasks) {
        this.userTasks = userTasks;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public TaskDTO addNewTask(@RequestBody TaskDTO taskDTO) throws UserNotFoundException {
        return userTasks.createTask(taskDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find")
    public TaskDTO findTaskById(@RequestParam Long id) throws UserNotFoundException, TaskNotFoundException {
        return userTasks.searchTaskById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Set<ResponseTaskDTO> searchTasks(@RequestParam String title) throws UserNotFoundException, TaskNotFoundException {
        return userTasks.searchTasksByTitle(title);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public List<TaskDTO> updateTask(@RequestBody List<TaskDTO> taskDTO) throws UserNotFoundException, TaskNotFoundException {
        return userTasks.updateTasks(taskDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public TaskDTO deleteTask(@RequestParam Long id) throws TaskNotFoundException, UserNotFoundException {
        return userTasks.deleteTask(id);
    }
}