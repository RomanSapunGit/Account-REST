package com.myproject.accountrest.accountinterface;

import com.myproject.accountrest.dto.SignInDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;


public interface UserAuth {

    UserDTO addNewUser(SignUpDTO signUpDto) throws RoleNotFoundException;

    SignInDTO signInUser(SignInDTO signInDTO);

    UserEntity findUserByAuth() throws UserNotFoundException;
}
