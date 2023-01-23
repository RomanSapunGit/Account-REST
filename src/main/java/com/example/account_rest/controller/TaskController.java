package com.example.account_rest.controller;

import com.example.account_rest.dto.RequestTaskDTO;
import com.example.account_rest.dto.ResponseTaskDTO;
import com.example.account_rest.dto.TaskDTO;
import com.example.account_rest.service.TaskService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")

public class TaskController {
    @Autowired
    private TaskService service;

    @SneakyThrows
    @PostMapping("/add_task")
    public ResponseEntity<TaskDTO> addNewTask(@RequestBody RequestTaskDTO requestTaskDTO) {
        return new ResponseEntity<>(service.createTask(requestTaskDTO), HttpStatus.OK);
    }

    @PutMapping("/show")
    public ResponseEntity<ResponseTaskDTO> showTasks(@RequestParam String username) {
        return new ResponseEntity<>(service.showTasks(username), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
