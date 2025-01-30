package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime taskDate;
    private LocalDate deadline;
    @ManyToOne
    private Account owner;
    @ManyToOne
    private Account performers;
    @ManyToOne
    private Team workTeam;
    @ManyToMany
    private List<Message> message;
}
