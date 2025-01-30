package com.example.Owl.s.Heart.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewTaskDTO {
    private String taskName;
    private String taskDescription;
    private LocalDate deadLine;
    private Long idTeam;
}
