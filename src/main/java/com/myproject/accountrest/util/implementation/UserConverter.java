package com.myproject.accountrest.util.implementation;

import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter implements com.myproject.accountrest.util.interfaces.UserConverter {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;

    @Autowired
    public UserConverter(PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDTO convertToUserDTO(UserEntity entity, UserDTO userDTO) {
        userDTO.setName(entity.getName());
        userDTO.setUsername(entity.getUsername());
        userDTO.setEmail(entity.getEmail());
        return userDTO;
    }

    @Override
    public UserEntity convertToUserEntity(SignUpDTO signUpDTO, UserEntity entity) {
        entity.setName(signUpDTO.getName());
        entity.setUsername(signUpDTO.getUsername());
        entity.setEmail(signUpDTO.getEmail());
        entity.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        return entity;
    }

    @Override
    public ResponseUserRoleDTO convertToResponseAuthorityDTO(UserEntity entity, ResponseUserRoleDTO response) {
        response.setName(entity.getName());
        response.setUsername(entity.getUsername());
        response.setRoles(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
        return response;
    }

    @Override
    public RoleEntity convertToRoleEntity(String role, RoleEntity roleEntity) {
        roleEntity.setName(role);
        roleRepo.save(roleEntity);
        return roleEntity;
    }
}
