package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.dto.AccountDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String reg(AccountDTO accountDTO) {
        Account account = new Account();
        account.setName(accountDTO.getName());
        account.setPassword(accountDTO.getPassword());
        account.setLinkTG(accountDTO.getLinkTG());
        account.setUsername(accountDTO.getUsername());
        accountService.create(account);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
