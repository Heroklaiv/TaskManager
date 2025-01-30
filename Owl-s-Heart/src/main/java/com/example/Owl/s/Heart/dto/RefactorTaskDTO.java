package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RefactorTaskDTO {
    Long id;
    String taskName;
    String taskDescription;
    LocalDate deadline;
    Long idPerformer;
    String performerName;
    List<Account> accountsTeam;
    Long teamId;
    String teamName;
    List<Team> teamsByOwner;
    short parameter;
}
