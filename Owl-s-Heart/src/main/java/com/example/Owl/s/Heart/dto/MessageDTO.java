package com.example.Owl.s.Heart.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private String text;
    private String nameAuthor;
    private LocalDateTime messageTime;
}
