package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime taskDate;
    private LocalDateTime deadline;
    @ManyToOne
    private Account performers;
    @OneToOne
    private Chat chat;
    @OneToOne
    private WorkPlace workPlace;
}
