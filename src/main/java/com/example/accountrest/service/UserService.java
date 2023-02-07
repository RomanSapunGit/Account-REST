package com.example.accountrest.service;

import com.example.accountrest.accountinterface.AccountConverter;
import com.example.accountrest.accountinterface.AccountUser;
import com.example.accountrest.dto.ChangeUserRoleDTO;
import com.example.accountrest.dto.ResponseAuthorityDTO;
import com.example.accountrest.dto.UserDTO;
import com.example.accountrest.entity.RoleEntity;
import com.example.accountrest.entity.UserEntity;
import com.example.accountrest.exception.RoleNotFoundException;
import com.example.accountrest.exception.UserNotFoundException;
import com.example.accountrest.exception.ValuesAreEqualException;
import com.example.accountrest.repository.RoleRepository;
import com.example.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements AccountUser {
    private final static String USER_DATA_UPDATED = ", data updated";
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountConverter converter;

    @Override
    public String updateUser(UserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException {
        UserEntity user = findUserByAuth();
        if (user.getUsername().equals(newUserData.getUsername()) && user.getEmail().equals(newUserData.getEmail())
                && user.getName().equals(newUserData.getName())) {
            throw new ValuesAreEqualException();
        }
        if (newUserData.getName() != null) {
            user.setName(newUserData.getName());
        }
        user.setUsername(newUserData.getUsername());
        user.setEmail(newUserData.getEmail());
        userRepo.save(user);
        return user.getName() + USER_DATA_UPDATED;
    }

    @Override
    public ResponseAuthorityDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
        UserEntity user = userRepo.findByUsername(changeUserRoleDTO.getUsername()).orElseThrow(UserNotFoundException::new);
        RoleEntity role = roleRepo.findByName(changeUserRoleDTO.getRole()).orElseThrow(RoleNotFoundException::new);
        Set<RoleEntity> userRoles = user.getRoles();
            if(changeUserRoleDTO.getAction().equals("add")) {
                userRoles.add(role);
            } else {
                userRoles.remove(role);
            }
        user.setRoles(userRoles);
        userRepo.save(user);
        return converter.convertToResponseAuthorityDTO(user);
    }

    private UserEntity findUserByAuth() throws UserNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName()).orElseThrow(UserNotFoundException::new);
    }
}
