package com.myproject.accountrest.repository;

import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @NotNull Optional<TaskEntity> findById(@NotNull Long id);
    TaskEntity getTaskEntityByUserAndId(UserEntity user, Long id);
    List<TaskEntity> getAllByCompleted(Boolean completed);

}
