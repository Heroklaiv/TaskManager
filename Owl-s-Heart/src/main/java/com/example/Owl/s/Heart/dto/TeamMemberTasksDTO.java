package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Task;
import com.example.Owl.s.Heart.entity.Team;
import lombok.Data;

import java.util.List;

@Data
public class TeamMemberTasksDTO {
    Account member;
    Team team;
    List<Task> tasksNoPerformers;
    List<Task> tasksEndDeadLine;
    List<Task> tasksWhenAccountPerformers;
}
