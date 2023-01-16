package com.example.accountRest.dto;

import com.example.accountRest.entity.TaskEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TaskDTO extends TaskEntity {
    private String task;
    private boolean completed;
}
