package com.example.accountrest.controller;

import com.example.accountrest.accountinterface.AccountUser;
import com.example.accountrest.accountinterface.UserAuth;
import com.example.accountrest.dto.ResetPassDTO;
import com.example.accountrest.dto.SignInDTO;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.exception.*;
import com.example.accountrest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final String IS_TAKEN_MESSAGE = "is already taken!";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuth userAuth;
    @Autowired
    private AccountUser accUser;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDTO signInDTO) {
        return new ResponseEntity<>(userAuth.signInUser(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) throws RoleNotFoundException {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>( signUpDto.getUsername() + IS_TAKEN_MESSAGE, HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>(signUpDto.getEmail() + IS_TAKEN_MESSAGE, HttpStatus.CONFLICT);
        }
            return new ResponseEntity<>(userAuth.addNewUser(signUpDto), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, HttpServletRequest request)
                                                 throws UnsupportedEncodingException, UserNotFoundException, MessagingException {
            userAuth.sendEmail(email, request);
            return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody ResetPassDTO resetPassDTO)
                                          throws UserNotFoundException, TokenExpiredException, ValuesAreNotEqualException {
            return new ResponseEntity<>(userAuth.resetPassword(token, resetPassDTO), HttpStatus.OK);
    }

    @PostMapping("/user/change-data")
    public ResponseEntity<?> changeUserData(@RequestBody UserDTO userDTO) throws UserNotFoundException, ValuesAreEqualException {
            return new ResponseEntity<>(accUser.updateUser(userDTO), HttpStatus.OK);
    }
}

