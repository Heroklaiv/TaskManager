package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;
    private String textMessage;
    private LocalDateTime timeMessage;
    @OneToOne
    private Chat chat;
    @OneToOne
    private Account author;
}

