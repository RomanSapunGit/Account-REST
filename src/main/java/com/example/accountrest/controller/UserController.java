package com.example.accountrest.controller;

import com.example.accountrest.accountinterface.AccountUser;
import com.example.accountrest.dto.ChangeUserRoleDTO;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AccountUser accountUser;
    @PostMapping("/change-authority")
    public ResponseEntity<?> changeUserAuthority(@RequestBody ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
       return new ResponseEntity<>(accountUser.deleteUserAuthority(changeUserRoleDTO), HttpStatus.OK);
    }
}
