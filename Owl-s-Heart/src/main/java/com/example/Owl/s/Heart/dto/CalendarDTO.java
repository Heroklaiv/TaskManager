package com.example.Owl.s.Heart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class CalendarDTO {
    public int year;
    public String month;
    public LocalDate today;
    public String dayOfWeek;
    public int sumTasksInControlNoPerformers;//1
    public int sumTasksInControl;//2
    public int sumTasksInExecutionNoPerformers;//3
    public int sumTasksInExecution;//4
    public int sumTasksInExecutionAccount;//4.1
    public int sumTasksEndDeadLinesAuthorNoPerformers;//5
    public int sumTasksEndDeadLinesAuthor;//6
    public int sumTasksEndDeadLinesExecutionNoPerformers;//7
    public int sumTasksEndDeadLinesExecution; //8
    public int sumTasksEndDeadLinesExecutionAccount; //8.1
    public List<DayOfCalendarDTO> days;
}
