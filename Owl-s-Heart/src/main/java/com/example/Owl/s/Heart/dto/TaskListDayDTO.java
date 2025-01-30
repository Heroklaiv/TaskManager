package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Task;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskListDayDTO {
    LocalDate date;
    List<Task> tasks;
}
