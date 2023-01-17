package com.example.accountRest.repository;

import com.example.accountRest.dto.TaskDTO;
import com.example.accountRest.entity.TaskEntity;
import com.example.accountRest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskDTO save(TaskDTO taskDTO);
    List<TaskDTO> getAllByUser(UserEntity user);
}
