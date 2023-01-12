package com.example.AccountRest.repository;

import com.example.AccountRest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    UserEntity  findByToken(String token);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

