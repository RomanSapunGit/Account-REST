package com.myproject.accountrest.controller;

import com.myproject.accountrest.service.interfaces.UserResetPass;
import com.myproject.accountrest.service.interfaces.UserAuthorization;
import com.myproject.accountrest.dto.ResetPassDTO;
import com.myproject.accountrest.dto.SignInDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.exception.*;
import com.myproject.accountrest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserResetPass resetPass;
    private final UserRepository userRepository;
    private final UserAuthorization userAuthorization;

    public AuthController(UserResetPass resetPass, UserRepository userRepository, UserAuthorization userAuthorization) {
        this.resetPass = resetPass;
        this.userRepository = userRepository;
        this.userAuthorization = userAuthorization;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDTO signInDTO) {
        return new ResponseEntity<>(userAuthorization.signInUser(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) throws RoleNotFoundException, UserDataAlreadyExistException {
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new UserDataAlreadyExistException(signUpDto.getEmail());
        } else if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new UserDataAlreadyExistException(signUpDto.getUsername());
        }
        return new ResponseEntity<>(userAuthorization.addNewUser(signUpDto), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, HttpServletRequest request)
            throws UnsupportedEncodingException, UserNotFoundException, MessagingException {
        resetPass.sendEmail(email, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody ResetPassDTO resetPassDTO)
            throws UserNotFoundException, TokenExpiredException, ValuesAreNotEqualException {
        return new ResponseEntity<>(resetPass.resetPassword(token, resetPassDTO), HttpStatus.OK);
    }
}

