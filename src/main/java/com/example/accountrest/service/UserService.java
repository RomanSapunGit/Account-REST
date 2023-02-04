package com.example.accountrest.service;

import com.example.accountrest.accountinterface.AccountUser;
import com.example.accountrest.dto.SignUpDTO;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AccountUser {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;

    @Override
    public String changeUserData(SignUpDTO newUserData) throws UserNotFoundException {
        UserEntity user = findUserByAuth();
        if (newUserData.getName() != null && newUserData.getUsername() != null
                && newUserData.getPassword() != null && newUserData.getEmail() != null) {
            user.setName(newUserData.getName());
            user.setUsername(newUserData.getUsername());
            user.setPassword(passwordEncoder.encode(newUserData.getPassword()));
            user.setEmail(newUserData.getEmail());
            userRepo.save(user);
        } else {
            if (newUserData.getName() != null) {
                user.setName(newUserData.getName());
                userRepo.save(user);
            } else if (newUserData.getUsername() != null) {
                user.setUsername(newUserData.getUsername());
                userRepo.save(user);
            } else if (newUserData.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(newUserData.getPassword()));
                userRepo.save(user);
            } else if (newUserData.getEmail() != null) {
                user.setEmail(newUserData.getEmail());
                userRepo.save(user);
            } else {
                return "Nothing to change";
            }
        }
        return "User data changed";
    }

    private UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}
