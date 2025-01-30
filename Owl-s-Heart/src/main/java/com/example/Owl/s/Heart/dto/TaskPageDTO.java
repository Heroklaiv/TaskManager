package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Message;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskPageDTO {
    String nameAccount;
    Long id;
    boolean statusOwner;
    boolean statusPerformer;
    String taskName;
    String taskDescription;
    String namePerformer;
    LocalDate deadline;
    LocalDateTime taskDate;
    String teamName;
    List<Account> members;
    Account owner;
    List<MessageDTO> messages;
}
