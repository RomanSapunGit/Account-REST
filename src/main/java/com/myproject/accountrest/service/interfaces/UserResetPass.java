package com.myproject.accountrest.service.interfaces;

import com.myproject.accountrest.dto.ResetPassDTO;
import com.myproject.accountrest.exception.TokenExpiredException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.exception.ValuesAreNotEqualException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface UserResetPass {
    ResetPassDTO resetPassword(String token, ResetPassDTO resetPassDTO) throws UserNotFoundException, ValuesAreNotEqualException, TokenExpiredException;

    void sendEmail(String email, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException, UserNotFoundException;
}
