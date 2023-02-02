package com.example.accountrest.accountinterface;

import com.example.accountrest.dto.ResetPassDTO;
import com.example.accountrest.dto.SignInDTO;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserAuth {

    void addNewUser(SignUpDTO signUpDto) throws RoleNotFoundException;

    String setTokensByEmail(String email) throws UserNotFoundException;

    String resetPassword(String token, ResetPassDTO resetPassDTO) throws UserNotFoundException;

    String sendEmail(String email, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, UserNotFoundException;

    String signInUser(SignInDTO signInDTO);
}
