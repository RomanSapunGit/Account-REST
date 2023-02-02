package com.example.accountrest.controller;

import com.example.accountrest.accountinterface.UserAuth;
import com.example.accountrest.dto.ResetPassDTO;
import com.example.accountrest.dto.SignInDTO;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuth userAuth;

    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInDTO signInDTO) {
        return new ResponseEntity<>(userAuth.signInUser(signInDTO), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.FOUND);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.FOUND);
        }
        try {
            userAuth.addNewUser(signUpDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }catch (RoleNotFoundException e){
            return new ResponseEntity<>("Role not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email,
                                                 HttpServletRequest request) throws UnsupportedEncodingException {
        try {
            return new ResponseEntity<>(userAuth.sendEmail(email, request), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (MessagingException e) {
            return new ResponseEntity<>("something went wrong, please try again later", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody ResetPassDTO resetPassDTO) {
        try {
            return new ResponseEntity<>(userAuth.resetPassword(token, resetPassDTO), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}

