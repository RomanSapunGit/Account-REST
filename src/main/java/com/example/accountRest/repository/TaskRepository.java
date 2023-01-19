package com.example.accountRest.repository;

import com.example.accountRest.entity.TaskEntity;
import com.example.accountRest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findById(Long id);

    List<TaskEntity> getAllByUser(UserEntity user);

}
