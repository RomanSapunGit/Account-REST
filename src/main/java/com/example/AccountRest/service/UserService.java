package com.example.AccountRest.service;

import com.example.AccountRest.dto.SignUpDTO;
import com.example.AccountRest.entity.RoleEntity;
import com.example.AccountRest.entity.UserEntity;
import com.example.AccountRest.repository.RoleRepository;
import com.example.AccountRest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void addNewUser(SignUpDTO signUpDto){
        UserEntity user = new UserEntity();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        RoleEntity roles = roleRepo.findByName("USER").orElse(null);
        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);
    }
}
