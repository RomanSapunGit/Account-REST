package com.example.accountRest.controller;

import com.example.accountRest.dto.RequestDTO;
import com.example.accountRest.dto.ResponseDTO;
import com.example.accountRest.dto.TaskDTO;
import com.example.accountRest.service.TaskService;
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
    public ResponseEntity<TaskDTO> addNewTask(@RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(service.createTask(requestDTO), HttpStatus.OK);
    }

    @PutMapping("/show")
    public ResponseEntity<ResponseDTO> showTasks(@RequestParam String username) {
        return new ResponseEntity<>(service.showTasks(username), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
