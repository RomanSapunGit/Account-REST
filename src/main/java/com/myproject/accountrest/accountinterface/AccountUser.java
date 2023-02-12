package com.myproject.accountrest.accountinterface;

import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.exception.ValuesAreEqualException;

public interface AccountUser {

    String updateUser(UserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException;

    ResponseUserRoleDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException;

}
