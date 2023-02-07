package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.ChangeUserRoleDTO;
import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.exception.ValuesAreEqualException;

public interface AccountUser {

    String updateUser(UserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException;

    ResponseAuthorityDTO addUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException;

    ResponseAuthorityDTO deleteUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException;
}
