package com.example.accountrest.service;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.accountinterface.AccountUser;
import com.example.accountrest.dto.UpdateUserDTO;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.exception.ValuesAreEqualException;
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
    @Autowired
    AccountConverter accConv;

    @Override
    public String updateUser(UpdateUserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException {
        UserEntity user = findUserByAuth();
        if(user.getUsername().equals(newUserData.getUsername()) && user.getEmail().equals(newUserData.getEmail())
                && user.getName().equals(newUserData.getName())){
            throw new ValuesAreEqualException();
        }
        user.setName(newUserData.getName());
        user.setUsername(newUserData.getUsername());
        user.setEmail(newUserData.getEmail());
            userRepo.save(user);
        return "User data changed";
    }

    private UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}
