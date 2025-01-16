package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime chatCreateDate;
    @OneToOne
    private Task task;
    @ManyToOne
    private Message message;
}
