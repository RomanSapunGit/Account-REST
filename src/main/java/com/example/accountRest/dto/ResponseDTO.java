package com.example.accountRest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ResponseDTO {
    private String username;
    List<TaskDTO> tasksList;

}
