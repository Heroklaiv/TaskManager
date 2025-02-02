package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private TaskService taskService;


    @GetMapping
    public String index(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        model.addAttribute("dto", taskService.getDataIndexPage(account));
        return "index";
    }
}
