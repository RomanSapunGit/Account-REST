package com.myproject.accountrest.controller;

import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.exception.UserDataAlreadyExistException;
import com.myproject.accountrest.service.interfaces.User;
import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final User user;

    @Autowired
    public UserController(User user) {
        this.user = user;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change-authority")
    public ResponseUserRoleDTO changeUserAuthority(@RequestBody ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
        return user.changeUserAuthority(changeUserRoleDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change-data")
    public UserDTO changeUserData(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        return user.updateUser(userDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-authorities")
    public List<String> createAuthority() throws UserDataAlreadyExistException {
        return user.createAuthorities();
    }
}
