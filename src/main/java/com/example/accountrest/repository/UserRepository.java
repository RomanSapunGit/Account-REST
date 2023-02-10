package com.example.accountrest.repository;

import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByToken(String token);
    UserEntity getByTask(TaskEntity task);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


}

