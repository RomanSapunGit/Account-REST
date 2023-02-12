package com.myproject.accountrest.controller;

import com.myproject.accountrest.accountinterface.AccountUser;
import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.exception.ValuesAreEqualException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AccountUser accountUser;
    @PostMapping("/change-authority")
    public ResponseEntity<?> changeUserAuthority(@RequestBody ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
       return new ResponseEntity<>(accountUser.changeUserAuthority(changeUserRoleDTO), HttpStatus.OK);
    }

    @PostMapping("/user/change-data")
    public ResponseEntity<?> changeUserData(@RequestBody UserDTO userDTO) throws UserNotFoundException, ValuesAreEqualException {
        return new ResponseEntity<>(accountUser.updateUser(userDTO), HttpStatus.OK);
    }
}
