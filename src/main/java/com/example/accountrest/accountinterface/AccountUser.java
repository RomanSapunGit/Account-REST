package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.UpdateUserDTO;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.exception.ValuesAreEqualException;

public interface AccountUser {

    String updateUser(UpdateUserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException;
}
