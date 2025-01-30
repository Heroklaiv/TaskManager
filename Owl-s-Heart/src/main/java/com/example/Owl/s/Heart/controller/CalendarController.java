package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.dto.CalendarDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String calendar(HttpSession session, Model model) {
        CalendarDTO calendar = taskService.getTasksInCalendar((Account) session.getAttribute("user"));
        model.addAttribute("calendar", calendar);
        return "calendar";
    }
}
