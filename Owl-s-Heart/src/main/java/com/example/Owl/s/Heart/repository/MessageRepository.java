package com.example.Owl.s.Heart.repository;


import com.example.Owl.s.Heart.entity.Message;
import com.example.Owl.s.Heart.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findById(Long id);
    Optional<List<Message>> findAllByTaskOrderByTimeMessage(Task task);
    @Transactional
    void deleteAllByTask(Task task);
}
