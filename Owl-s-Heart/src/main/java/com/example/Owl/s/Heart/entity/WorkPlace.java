package com.example.Owl.s.Heart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class WorkPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String entity;
    @OneToOne
    private Account admin;
    @OneToOne
    private Team workTeam;
    @ManyToOne
    private Task tasks;
}
