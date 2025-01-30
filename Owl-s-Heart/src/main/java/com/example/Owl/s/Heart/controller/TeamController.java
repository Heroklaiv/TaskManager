package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.dto.CreateTeamDTO;
import com.example.Owl.s.Heart.dto.TeamPageDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.service.AccountService;
import com.example.Owl.s.Heart.service.TeamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String teamPage(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        TeamPageDTO teamPageDTO = teamService.createPageTeam(account);
        model.addAttribute("TeamAdmin", teamPageDTO.getTeamListWhenAccountAdmin());
        model.addAttribute("TeamMembers", teamPageDTO.getTeamListWhenAccountMembers());
        return "team";
    }

    @GetMapping("/create")
    public String teamCreatePage() {
        return "create_team";
    }

    @PostMapping("/create")
    public String teamCreate(CreateTeamDTO createTeamDTO, HttpSession session) {
        teamService.createNewTeam((Account) session.getAttribute("user"), createTeamDTO.getTeamName());
        return "redirect:/team";
    }

    @GetMapping("/refactor/{id}")
    public String teamRefactorPage(@PathVariable("id") Long id, Model model, HttpSession session) {
        Team team = teamService.getTeamById(id);
        Account account = team.getAdmin();
        Account accountSession = (Account) session.getAttribute("user");
        if (account.getId() != accountSession.getId()) {
            model.addAttribute("message", "нельзя так делать");
            return "error";
        } else {
            model.addAttribute("Team", team);
            return "refactor_team";
        }
    }

    @PostMapping("/update")
    public String updateTeam(String teamName, String username, Long idTeam, HttpSession session) {
        if (teamName != null) {
            teamService.updateTeamName(idTeam, teamName);
        }
        if(username != null) {
            teamService.updateTeamMembers(idTeam, username);
        }
        return "redirect:/team/refactor/" + idTeam;

    }

}
