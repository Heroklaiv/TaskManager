package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.dto.*;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Message;
import com.example.Owl.s.Heart.entity.Task;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.repository.AccountRepository;
import com.example.Owl.s.Heart.repository.TaskRepository;
import com.example.Owl.s.Heart.repository.TeamRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TeamService teamService;
    private final TeamRepository teamRepository;
    private final MessageService messageService;
    private final AccountRepository accountRepository;

    public TaskService(TaskRepository taskRepository, TeamService teamService, TeamRepository teamRepository, MessageService messageService, AccountRepository accountRepository) {
        this.taskRepository = taskRepository;
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.messageService = messageService;
        this.accountRepository = accountRepository;
    }


    public CreateTaskDTO generatePageCreateTask(Account account) {
        return teamService.createListTeamWhenAccountAdmin(account);
    }

    public void saveTask(@NotNull NewTaskDTO newTaskDTO, Account author) {
        Task task = new Task();
        task.setName(newTaskDTO.getTaskName());
        task.setDescription(newTaskDTO.getTaskDescription());
        task.setDeadline(newTaskDTO.getDeadLine());
        task.setWorkTeam(teamRepository.findById(newTaskDTO.getIdTeam()).get());
        task.setTaskDate(LocalDateTime.now());
        task.setOwner(author);
        Task saveTask = taskRepository.save(task);
        messageService.createAndSaveMessage("Задача создана!", author, saveTask);
    }

    public TaskPageDTO getPageTask(Long id, Account author) {
        TaskPageDTO taskPageDTO = new TaskPageDTO();
        Task task = taskRepository.findById(id).get();
        taskPageDTO.setId(id);
        taskPageDTO.setTaskName(task.getName());
        taskPageDTO.setTaskDescription(task.getDescription());
        taskPageDTO.setDeadline(task.getDeadline());
        taskPageDTO.setTaskDate(task.getTaskDate());
        taskPageDTO.setOwner(task.getOwner());
        taskPageDTO.setStatusOwner(task.getOwner() == author);
        Team team = task.getWorkTeam();
        taskPageDTO.setMembers(team.getMember());
        if (task.getPerformers() == null) {
            taskPageDTO.setNamePerformer("ВНИМАНИЕ, он не назначен!");
            taskPageDTO.setStatusPerformer(false);
        } else {
            taskPageDTO.setNamePerformer(task.getPerformers().getName());
            taskPageDTO.setStatusPerformer(true);
        }
        List<Message> messageList = messageService.getAllMessagesTaskSortedByTime(task);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message : messageList) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessageTime(message.getTimeMessage());
            messageDTO.setText(message.getTextMessage());
            messageDTO.setNameAuthor(message.getAuthor().getName());
            messageDTOList.add(messageDTO);
        }
        taskPageDTO.setMessages(messageDTOList);
        taskPageDTO.setTeamName(team.getName());
        taskPageDTO.setNameAccount(author.getName());
        return taskPageDTO;
    }

    public CalendarDTO getTasksInCalendar(Account author) {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();//название месяца
        String monthString = convertDayOfMonth(month);//на русский язык
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setYear(date.getYear());
        calendarDTO.setMonth(monthString);
        calendarDTO.setToday(date);
        DayOfWeek todayOfWeek = date.getDayOfWeek();//день недели
        String todayDayOfWeek = convertDayOfWeek(todayOfWeek.toString());
        calendarDTO.setDayOfWeek(todayDayOfWeek);
        int daysInCurrentMonth = date.lengthOfMonth();//всего в месяце
        calendarDTO.setSumTasksInControlNoPerformers((taskRepository.findTaskByOwnerAndPerformersIsNullAndDeadline(author, date).get()).size());
        calendarDTO.setSumTasksInControl((taskRepository.findTaskByOwnerAndPerformersIsNotNullAndDeadline(author, date).get()).size());
        calendarDTO.setSumTasksEndDeadLinesAuthorNoPerformers((taskRepository.findTaskByOwnerAndPerformersIsNullAndDeadlineBefore(author, date).get()).size());
        calendarDTO.setSumTasksEndDeadLinesAuthor((taskRepository.findTaskByOwnerAndPerformersIsNotNullAndDeadlineBefore(author, date).get()).size());
        List<Team> teamList = teamService.getTeamsByMember(author);
        for (Team team : teamList) {
            calendarDTO.setSumTasksInExecutionNoPerformers((taskRepository.findByWorkTeamAndPerformersIsNullAndDeadline(team, date).get()).size());
            calendarDTO.setSumTasksInExecution((taskRepository.findByWorkTeamAndPerformersIsNotNullAndDeadline(team, date).get()).size());
            calendarDTO.setSumTasksInExecutionAccount((taskRepository.findByWorkTeamAndPerformersAndDeadline(team, author, date).get()).size());
            calendarDTO.setSumTasksEndDeadLinesExecutionNoPerformers((taskRepository.findByWorkTeamAndPerformersIsNullAndDeadlineBefore(team, date).get()).size());
            calendarDTO.setSumTasksEndDeadLinesExecution((taskRepository.findByWorkTeamAndPerformersIsNotNullAndDeadlineBefore(team, date).get()).size());
            calendarDTO.setSumTasksEndDeadLinesExecutionAccount((taskRepository.findByWorkTeamAndPerformersAndDeadlineBefore(team, author, date).get()).size());
        }
        int numberDayToday = date.getDayOfMonth();
        List<DayOfCalendarDTO> dayOfCalendarDTOList = new ArrayList<>();
        for (int i = 1; i <= (daysInCurrentMonth - numberDayToday); i++) {
            LocalDate localDateTasks = date.plusDays(i);
            DayOfCalendarDTO dayOfCalendarDTO = new DayOfCalendarDTO();
            dayOfCalendarDTO.setDay(localDateTasks);
            DayOfWeek dayOfWeek = localDateTasks.getDayOfWeek();//день недели
            String dayWeek = dayOfWeek.toString();
            dayOfCalendarDTO.setDayOfWeek(convertDayOfWeek(dayWeek));// на русский язык
            dayOfCalendarDTO.setSumTasksControl(taskRepository.findTaskByDeadlineAndOwner(localDateTasks, author).get().size());
            int sum = 0;
            for (Team team : teamList) {
                sum = sum + taskRepository.findByWorkTeamAndDeadline(team, localDateTasks).get().size();
            }
            dayOfCalendarDTO.setSumTasksMembers(sum);
            dayOfCalendarDTOList.add(dayOfCalendarDTO);
        }
        calendarDTO.setDays(dayOfCalendarDTOList);
        return calendarDTO;
    }

    public TaskListDayDTO getTaskListOwnerDay(Account author, LocalDate date) {
        TaskListDayDTO taskListDayDTO = new TaskListDayDTO();
        List<Task> taskList = taskRepository.findTaskByDeadlineAndOwner(date, author).get();
        taskListDayDTO.setTasks(taskList);
        taskListDayDTO.setDate(date);
        return taskListDayDTO;
    }

    public TaskListDayDTO getTaskListMemberDay(Account author, LocalDate date) {
        TaskListDayDTO taskListDayDTO = new TaskListDayDTO();
        List<Team> teamList = teamService.getTeamsByMember(author);
        List<Task> taskList = new ArrayList<>();
        for (Team team : teamList) {
            taskList.addAll(taskRepository.findByWorkTeamAndDeadline(team, date).get());
        }
        taskListDayDTO.setTasks(taskList);
        taskListDayDTO.setDate(date);
        return taskListDayDTO;
    }

    public TaskListDayDTO getTaskListOwnerDeadline(Account author, LocalDate date) {
        TaskListDayDTO taskListDayDTO = new TaskListDayDTO();
        List<Task> taskList = taskRepository.findTaskByOwnerAndDeadlineBefore(author, date).get();
        taskListDayDTO.setTasks(taskList);
        taskListDayDTO.setDate(date);
        return taskListDayDTO;
    }

    public TaskListDayDTO getTaskListMemberDeadline(Account author, LocalDate date) {
        TaskListDayDTO taskListDayDTO = new TaskListDayDTO();
        List<Team> teamList = teamService.getTeamsByMember(author);
        List<Task> taskList = new ArrayList<>();
        for (Team team : teamList) {
            taskList.addAll(taskRepository.findTaskByWorkTeamAndDeadlineBefore(team, date).get());
        }
        taskListDayDTO.setTasks(taskList);
        taskListDayDTO.setDate(date);
        return taskListDayDTO;
    }

    public Task findByID(Long id) {
        return taskRepository.findById(id).get();
    }

    public RefactorTaskDTO getDataRefactorTask(Long id, Account account) {
        RefactorTaskDTO refactorTaskDTO = new RefactorTaskDTO();
        Task task = taskRepository.findById(id).get();
        if (task.getPerformers() == null) {
            refactorTaskDTO.setPerformerName("не назначен");
            refactorTaskDTO.setIdPerformer(0L);
        } else {
            refactorTaskDTO.setPerformerName(task.getPerformers().getName());
            refactorTaskDTO.setIdPerformer(task.getPerformers().getId());
        }
        refactorTaskDTO.setId(id);
        refactorTaskDTO.setTaskName(task.getName());
        refactorTaskDTO.setTaskDescription(task.getDescription());
        refactorTaskDTO.setAccountsTeam(teamRepository.findById(task.getWorkTeam().getId()).get().getMember());
        refactorTaskDTO.setTeamsByOwner((teamRepository.findAllByAdmin(account)).get());
        return refactorTaskDTO;
    }

    public void setPerformer(Long id, Account account) {
        Task task = taskRepository.findById(id).get();
        task.setPerformers(account);
        taskRepository.save(task);
    }

    public void setNewNameTask(Long id, String name) {
        Task task = taskRepository.findById(id).get();
        task.setName(name);
        taskRepository.save(task);
    }

    public void setNewDescriptionTask(Long id, String description) {
        Task task = taskRepository.findById(id).get();
        task.setDescription(description);
        taskRepository.save(task);
    }

    public void setNewDeadLine(Long id, LocalDate date) {
        Task task = taskRepository.findById(id).get();
        task.setDeadline(date);
        taskRepository.save(task);
    }

    public void setNewPerformer(Long id, Long idAccount) {
        Account acc = accountRepository.findById(idAccount).get();
        Task task = taskRepository.findById(id).get();
        task.setPerformers(acc);
        taskRepository.save(task);
    }

    public void setNewTeam(Long id, Long idTeam) {
        Team team = teamRepository.findById(idTeam).get();
        Task task = taskRepository.findById(id).get();
        task.setWorkTeam(team);
        taskRepository.save(task);
    }

    public void deleteTask(Long idTask) {
        messageService.deleteMessageByTask(taskRepository.findById(idTask).get());
        taskRepository.deleteById(idTask);
    }

    public IndexPageDTO getDataIndexPage(Account account) {
        IndexPageDTO indexPageDTO = new IndexPageDTO();
        //сегодня
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        String monthString = convertDayOfMonthIndex(month);
        DayOfWeek todayOfWeek = date.getDayOfWeek();
        String todayDayOfWeek = convertDayOfWeek(todayOfWeek.toString());
        String thisDay = "сегодня, " + date.getDayOfMonth() + " " + monthString + ", " +
                todayDayOfWeek;
        indexPageDTO.setThisDay(thisDay);
        indexPageDTO.setTasksControlThisDay(getTaskListOwnerDay(account, date));
        indexPageDTO.setTasksPerformThisDay(getTaskListMemberDay(account, date));
        indexPageDTO.setTasksControlThisDayBeforeDeadLine(getTaskListOwnerDeadline(account, date));
        indexPageDTO.setTasksPerformThisDayBeforeDeadLine(getTaskListMemberDeadline(account, date));
        //завтра
        LocalDate nextDay = date.plusDays(1);
        int month1 = nextDay.getMonthValue();
        String monthString1 = convertDayOfMonthIndex(month1);
        DayOfWeek todayOfWeek1 = nextDay.getDayOfWeek();
        String todayDayOfWeek1 = convertDayOfWeek(todayOfWeek1.toString());
        String thisDay1 = "завтра, " + nextDay.getDayOfMonth() + " " + monthString1 + ", " +
                todayDayOfWeek1;
        indexPageDTO.setNextDay(thisDay1);
        indexPageDTO.setTasksControlNextDay(getTaskListOwnerDay(account, nextDay));
        indexPageDTO.setTasksPerformNextDay(getTaskListMemberDay(account, nextDay));
        //следующие 5 дней
        TaskListDayDTO taskListDayDTOControl = new TaskListDayDTO();
        TaskListDayDTO taskListDayDTOMember = new TaskListDayDTO();
        for (int i = 2; i <= 6; i++) {
            LocalDate day = date.plusDays(i);
            if (taskListDayDTOControl.getTasks() == null) {
                taskListDayDTOControl.setTasks(new ArrayList<>());
                taskListDayDTOMember.setTasks(new ArrayList<>());
            }
            List<Task> taskListMember = taskListDayDTOMember.getTasks();
            taskListMember.addAll(getTaskListMemberDay(account, day).getTasks());
            taskListDayDTOMember.setTasks(taskListMember);
            List<Task> taskListControl = taskListDayDTOControl.getTasks();
            taskListControl.addAll(getTaskListOwnerDay(account, day).getTasks());
            taskListDayDTOControl.setTasks(taskListControl);
        }
        indexPageDTO.setTasksControlNextWeek(taskListDayDTOControl);
        indexPageDTO.setTasksPerformNextWeek(taskListDayDTOMember);
        indexPageDTO.setNextWeek("с " + date.plusDays(2) + " по " + date.plusDays(6));
        return indexPageDTO;
    }

    @Contract(pure = true)
    public @NotNull String convertDayOfWeek(@NotNull String dayOfWeek) {
        return switch (dayOfWeek) {
            case "SUNDAY" -> "Воскресенье";
            case "MONDAY" -> "Понедельник";
            case "TUESDAY" -> "Вторник";
            case "WEDNESDAY" -> "Среда";
            case "THURSDAY" -> "Четверг";
            case "FRIDAY" -> "Пятница";
            case "SATURDAY" -> "Суббота";
            default -> "error day";
        };
    }

    @Contract(pure = true)
    public @NotNull String convertDayOfMonth(int month) {
        return switch (month) {
            case 1 -> "Январь";
            case 2 -> "Февраль";
            case 3 -> "Март";
            case 4 -> "Апрель";
            case 5 -> "Май";
            case 6 -> "Июнь";
            case 7 -> "Июль";
            case 8 -> "Август";
            case 9 -> "Сентябрь";
            case 10 -> "Октябрь";
            case 11 -> "Ноябрь";
            case 12 -> "Декабрь";
            default -> "error month";
        };
    }
    @Contract(pure = true)
    public @NotNull String convertDayOfMonthIndex(int month) {
        return switch (month) {
            case 1 -> "Января";
            case 2 -> "Февраля";
            case 3 -> "Марта";
            case 4 -> "Апреля";
            case 5 -> "Мая";
            case 6 -> "Июня";
            case 7 -> "Июля";
            case 8 -> "Августа";
            case 9 -> "Сентября";
            case 10 -> "Октября";
            case 11 -> "Ноября";
            case 12 -> "Декабря";
            default -> "error month";
        };
    }
}
