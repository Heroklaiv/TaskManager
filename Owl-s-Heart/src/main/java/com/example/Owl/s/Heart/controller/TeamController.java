package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @GetMapping
    public String teamPage(HttpSession session) {
       Account account = (Account) session.getAttribute("user");
       System.out.println(teamService.createPageTeam(account));

        return "team";
    }
}
