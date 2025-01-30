package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TeamService teamService;

    @GetMapping()
    public String test(HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        System.out.println(teamService.getTeamsByMember(account));
        return "test";
    }
}
