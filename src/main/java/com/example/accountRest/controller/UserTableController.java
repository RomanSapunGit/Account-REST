package com.example.accountRest.controller;

import com.example.accountRest.dto.LoginDTO;
import com.example.accountRest.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/users")

public class UserTableController {

    @PostMapping("/show_table")
    public ResponseEntity<String> showTable(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(loginDTO.getUsernameOrEmail(), HttpStatus.OK);
    }
}
