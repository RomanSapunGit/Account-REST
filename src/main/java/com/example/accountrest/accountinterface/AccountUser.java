package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.exception.UserNotFoundException;

public interface AccountUser {

    String changeUserData(SignUpDTO newUserData) throws UserNotFoundException;
}
