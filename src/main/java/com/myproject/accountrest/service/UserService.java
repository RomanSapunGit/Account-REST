package com.myproject.accountrest.service;

import com.myproject.accountrest.accountinterface.UserConverter;
import com.myproject.accountrest.accountinterface.User;
import com.myproject.accountrest.accountinterface.UserAuthorization;
import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.exception.ValuesAreEqualException;
import com.myproject.accountrest.repository.RoleRepository;
import com.myproject.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements User {
    private final static String USER_DATA_UPDATED = ", data updated";
    @Autowired
    private UserAuthorization auth;
    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserConverter userConverter;

    @Override
    public String updateUser(UserDTO newUserData) throws UserNotFoundException, ValuesAreEqualException {
        UserEntity user = auth.findUserByAuth();
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
    public ResponseUserRoleDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
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
        return userConverter.convertToResponseAuthorityDTO(user);
    }
}
