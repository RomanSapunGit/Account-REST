package com.example.accountrest.repository;

import com.example.accountrest.entity.TaskEntity;
import com.example.accountrest.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @NotNull Optional<TaskEntity> findById(@NotNull Long id);
    List<TaskEntity> getAllByUser(UserEntity user);
    List<TaskEntity> getAllByCompleted(Boolean completed);

}
