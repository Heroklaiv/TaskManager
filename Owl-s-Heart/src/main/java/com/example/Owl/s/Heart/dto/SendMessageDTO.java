package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Account;
import lombok.Data;

@Data
public class SendMessageDTO {
    String messageText;
    Account author;
    Long taskID;
}
