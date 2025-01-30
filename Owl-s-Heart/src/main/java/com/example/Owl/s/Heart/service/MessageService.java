package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.entity.Account;

import com.example.Owl.s.Heart.entity.Message;
import com.example.Owl.s.Heart.entity.Task;
import com.example.Owl.s.Heart.repository.MessageRepository;
import com.example.Owl.s.Heart.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Message createAndSaveMessage(String textMessage, Account author, Task task) {
        Message message = new Message();
        message.setAuthor(author);
        message.setTextMessage(textMessage);
        message.setTimeMessage(LocalDateTime.now());
        message.setTask(task);
        Message message1 = messageRepository.save(message);
        Task task1 = taskRepository.findById(task.getId()).get();
        if (task1.getMessage() == null) {
            List<Message> messageList = new ArrayList<>();
            messageList.add(message1);
            task1.setMessage(messageList);
            taskRepository.save(task1);
        } else {
            task1.getMessage().add(message1);
            taskRepository.save(task1);
        }

        taskRepository.save(task1);
        return message1;

    }

    public List<Message> getAllMessagesTaskSortedByTime(Task task) {
        return messageRepository.findAllByTaskOrderByTimeMessage(task).get();
    }

    public void deleteMessageByTask(Task task) {

        messageRepository.deleteAllByTask(task);
    }
}
