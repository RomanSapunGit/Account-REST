package com.myproject.accountrest.controller;

import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.service.interfaces.UserResetPass;
import com.myproject.accountrest.service.interfaces.UserAuthorization;
import com.myproject.accountrest.dto.ResetPassDTO;
import com.myproject.accountrest.dto.SignInDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.exception.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserResetPass resetPass;
    private final UserAuthorization userAuthorization;

    public AuthController(UserResetPass resetPass, UserAuthorization userAuthorization) {
        this.resetPass = resetPass;
        this.userAuthorization = userAuthorization;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-in")
    public SignInDTO authenticateUser(@RequestBody SignInDTO signInDTO) {
        return userAuthorization.signInUser(signInDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/signup")
    public UserDTO registerUser(@RequestBody SignUpDTO signUpDto) throws UserDataAlreadyExistException {
        return userAuthorization.addNewUser(signUpDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reset-password")
    public void forgotPassword(@RequestParam String email, HttpServletRequest request)
            throws UnsupportedEncodingException, UserNotFoundException, MessagingException {
        resetPass.sendEmail(email, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reset-password")
    public ResetPassDTO resetPassword(@RequestParam String token, @RequestBody ResetPassDTO resetPassDTO)
            throws UserNotFoundException, ValuesAreNotEqualException {
        return resetPass.resetPassword(token, resetPassDTO);
    }
}

