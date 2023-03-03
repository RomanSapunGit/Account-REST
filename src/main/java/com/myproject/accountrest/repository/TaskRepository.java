package com.myproject.accountrest.repository;

import com.myproject.accountrest.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findById(Long id);

    List<TaskEntity> getFirst20ByCompleted(Boolean completed);
}
