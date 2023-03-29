package com.myproject.accountrest.service.implementation;

import com.myproject.accountrest.exception.UserDataAlreadyExistException;
import com.myproject.accountrest.util.interfaces.UserConverter;
import com.myproject.accountrest.service.interfaces.User;
import com.myproject.accountrest.service.interfaces.UserAuthorization;
import com.myproject.accountrest.dto.ChangeUserRoleDTO;
import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.RoleEntity;
import com.myproject.accountrest.entity.UserEntity;
import com.myproject.accountrest.exception.RoleNotFoundException;
import com.myproject.accountrest.exception.UserNotFoundException;
import com.myproject.accountrest.repository.RoleRepository;
import com.myproject.accountrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class UserService implements User {
    private static final List<String> ROLES = Arrays.asList("USER_ROLE", "ROLE_ADMIN", "ROLE_DISABLE");
    private final UserAuthorization auth;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserAuthorization auth, RoleRepository roleRepo, UserRepository userRepo, UserConverter userConverter) {
        this.auth = auth;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO updateUser(UserDTO newUserData) throws UserNotFoundException {
        UserEntity user = auth.findUserByAuth();
        user.setUsername(newUserData.getUsername());
        user.setEmail(newUserData.getEmail());
        userRepo.save(user);
        return newUserData;
    }

    @Override
    public ResponseUserRoleDTO changeUserAuthority(ChangeUserRoleDTO changeUserRoleDTO) throws UserNotFoundException, RoleNotFoundException {
        UserEntity user = userRepo.findByUsername(changeUserRoleDTO.getUsername()).orElseThrow(UserNotFoundException::new);
        RoleEntity role = roleRepo.findByName(changeUserRoleDTO.getRole()).orElseThrow(RoleNotFoundException::new);
        if (changeUserRoleDTO.getAction().equals("add")) {
            user.getRoles().add(role);
        } else {
            user.getRoles().remove(role);
        }
        userRepo.save(user);
        return userConverter.convertToResponseAuthorityDTO(user, new ResponseUserRoleDTO());
    }

    @Override
    public List<String> createAuthorities() throws UserDataAlreadyExistException {
        List<String> listOfRoles = ROLES.stream()
                .filter(role -> !roleRepo.existsByName(role))
                .map(role -> userConverter.convertToRoleEntity(role, new RoleEntity()).getName())
                .toList();
        if (listOfRoles.isEmpty()) {
            throw new UserDataAlreadyExistException(ROLES.toString());
        }
        return listOfRoles;
    }
}
