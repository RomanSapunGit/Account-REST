package com.myproject.accountrest.util.interfaces;


import com.myproject.accountrest.dto.ResponseUserRoleDTO;
import com.myproject.accountrest.dto.SignUpDTO;
import com.myproject.accountrest.dto.UserDTO;
import com.myproject.accountrest.entity.UserEntity;


public interface UserConverter {

    UserDTO convertToUserDTO(UserEntity entity, UserDTO userDTO);

    UserEntity convertToUserEntity(SignUpDTO signUpDTO, UserEntity entity);

    ResponseUserRoleDTO convertToResponseAuthorityDTO(UserEntity entity);

}
