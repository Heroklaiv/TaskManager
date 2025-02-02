package com.example.Owl.s.Heart.controller;

import com.example.Owl.s.Heart.dto.NewTaskDTO;
import com.example.Owl.s.Heart.dto.RefactorTaskDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.service.MessageService;
import com.example.Owl.s.Heart.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String getTask(Long id, HttpSession session, Model model) {
        model.addAttribute("dto", taskService.getPageTask(id, (Account) session.getAttribute("user")));

        return "task";
    }

    @GetMapping("/create")
    public String taskCreate(HttpSession session, Model model) {
        model.addAttribute("listTeams", taskService.generatePageCreateTask((Account) session.getAttribute("user")).getTeamListWhenAccountAdmin());
        return "task_create_page";
    }

    @PostMapping("/create")
    public String taskPost(NewTaskDTO newTaskDTO, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        taskService.saveTask(newTaskDTO, account);
        return "redirect:/";
    }

    @GetMapping("/owner")
    public String taskOwnerDay(LocalDate date, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        model.addAttribute("dto", taskService.getTaskListOwnerDay(account, date));
        return "tasks_on_this_day";
    }

    @GetMapping("/member")
    public String taskMemberDay(LocalDate date, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        model.addAttribute("dto", taskService.getTaskListMemberDay(account, date));
        return "tasks_on_this_day";
    }

    @GetMapping("/owner/deadline")
    public String taskEndDeadlineOwnerDay(LocalDate date, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        model.addAttribute("dto", taskService.getTaskListOwnerDeadline(account, date));
        return "tasks_on_this_day";
    }

    @GetMapping("/member/deadline")
    public String taskEndDeadlineMemberDay(LocalDate date, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        model.addAttribute("dto", taskService.getTaskListMemberDeadline(account, date));
        return "tasks_on_this_day";
    }

    @PostMapping("/sendMessage")
    public String taskSendMessage(String messageText, Long taskId, HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        messageService.createAndSaveMessage(messageText, account, taskService.findByID(taskId));
        String url = "/task?id=" + taskId;
        return "redirect:" + url;
    }

    @GetMapping("/refactor")
    public String taskRefactor(Long id, String parameter, HttpSession session, Model model) {
        Account account = (Account) session.getAttribute("user");
        if (parameter.equals("perform")){
            taskService.setPerformer(id, account);
            return "redirect:/task?id=" + id;
        }
        RefactorTaskDTO refactorTaskDTO = taskService.getDataRefactorTask(id, account);
        switch (parameter) {
            case "name" -> refactorTaskDTO.setParameter((short) 1);
            case "description" -> refactorTaskDTO.setParameter((short) 2);
            case "deadline" -> refactorTaskDTO.setParameter((short) 3);
            case "performer" -> refactorTaskDTO.setParameter((short) 4);
            case "team" -> refactorTaskDTO.setParameter((short) 6);
            default -> refactorTaskDTO.setParameter((short) 7);
        }
        model.addAttribute("dto", refactorTaskDTO);
        return "task_refactor";
    }

    @PostMapping("/refactor")
    public String taskRefactorPost(Long id, String parameter, String taskName, String taskDescription,
                                   LocalDate deadLine, Long idAccount, Long idTeam) {
        switch (parameter) {
            case "name" -> taskService.setNewNameTask(id,taskName);
            case "description" -> taskService.setNewDescriptionTask(id,taskDescription);
            case "deadLine" -> taskService.setNewDeadLine(id,deadLine);
            case "performer" -> taskService.setNewPerformer(id,idAccount);
            case "team" -> taskService.setNewTeam(id,idTeam);
        }
        return "redirect:/task?id=" + id;
    }

    @PostMapping("/delete")
    public String taskDelete(Long id) {
        taskService.deleteTask(id);
        return "redirect:/calendar";

    }

}
