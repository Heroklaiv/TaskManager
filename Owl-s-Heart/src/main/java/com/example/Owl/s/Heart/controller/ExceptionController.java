package com.example.Owl.s.Heart.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(RuntimeException.class)
        public String exceptionHandler(RuntimeException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String exceptionHandler(EmptyResultDataAccessException ex, Model model) {
        model.addAttribute("message", "Неверный Запрос!");
        return "error";
    }
}

