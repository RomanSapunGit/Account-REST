package com.example.account_rest.controller;

import com.example.account_rest.dto.SignInDTO;
import com.example.account_rest.dto.SignUpDTO;
import com.example.account_rest.repository.UserRepository;
import com.example.account_rest.service.UserService;
import com.example.account_rest.utility.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService service;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInDTO signInDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.getUsername(), signInDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        service.addNewUser(signUpDto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

    @PostMapping("/forgot_password")
    public ResponseEntity<String> forgotPassword(@RequestBody SignUpDTO signUpDTO,
                                                 HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String email = signUpDTO.getEmail();
        String response = service.forgotPassword(email);
        if (!response.startsWith("Invalid")) {
            String resetPasswordLink = Utility.getSiteURL(request) + "/api/user/reset_password?token=" + response;
            service.sendEmail(email, resetPasswordLink);
        }
        return new ResponseEntity<>("We have sent your token on email", HttpStatus.OK);
    }

    @PutMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(service.resetPassword(signUpDTO.getToken(), signUpDTO.getPassword()), HttpStatus.OK);
    }
}

