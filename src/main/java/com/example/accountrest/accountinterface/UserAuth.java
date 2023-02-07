package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.ResetPassDTO;
import com.example.accountrest.dto.SignInDTO;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.TokenExpiredException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.exception.ValuesAreNotEqualException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserAuth {

    UserDTO addNewUser(SignUpDTO signUpDto) throws RoleNotFoundException;

    String setTokensByEmail(String email) throws UserNotFoundException;

    ResetPassDTO resetPassword(String token, ResetPassDTO resetPassDTO) throws UserNotFoundException, ValuesAreNotEqualException, TokenExpiredException;

    void sendEmail(String email, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, UserNotFoundException;

    SignInDTO signInUser(SignInDTO signInDTO);
}
