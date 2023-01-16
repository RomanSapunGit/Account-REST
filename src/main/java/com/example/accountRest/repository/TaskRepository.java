package com.example.accountRest.repository;

import com.example.accountRest.dto.TaskDTO;
import com.example.accountRest.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskDTO save(TaskDTO taskDTO);
}
