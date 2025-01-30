package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;
    private String textMessage;
    private LocalDateTime timeMessage;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Task task;
    @ManyToOne
    private Account author;
}

