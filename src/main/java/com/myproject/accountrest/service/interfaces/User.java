package com.myproject.accountrest.service.interfaces;

import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.exception.ValuesAreEqualException;

public interface User {

    UserDTO updateUser(UserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException;

    ResponseUserRoleDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException;

}
