package com.myproject.accountrest.service.interfaces;

import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.exception.UserDataAlreadyExistException;
import com.myproject.accountrest.exception.UserNotFoundException;

import java.util.List;

public interface User {

    UserDTO updateUser(UserDTO newUserData) throws UserNotFoundException;

    ResponseUserRoleDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException;

    List<String> createAuthorities() throws UserDataAlreadyExistException;
}
