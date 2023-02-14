package com.myproject.accountrest.util;

import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter implements com.myproject.accountrest.accountinterface.UserConverter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO convertToUserDTO(UserEntity entity, UserDTO userDTO) {
        userDTO.setName(entity.getName());
        userDTO.setUsername(entity.getUsername());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }

    @Override
    public UserEntity convertToUserEntity(SignUpDTO signUpDTO, UserEntity entity) {
        entity.setName(entity.getName());
        entity.setUsername(entity.getUsername());
        entity.setEmail(entity.getEmail());
        entity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        return entity;
    }

    @Override
    public ResponseUserRoleDTO convertToResponseAuthorityDTO(UserEntity entity) {
        ResponseUserRoleDTO response = new ResponseUserRoleDTO();
        response.setName(entity.getName());
        response.setUsername(entity.getUsername());
        response.setRoles(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
        return response;
    }

}
