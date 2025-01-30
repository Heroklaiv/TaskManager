package com.example.Owl.s.Heart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DayOfCalendarDTO {
    LocalDate day;
    String dayOfWeek;
    int sumTasksMembers;
    int sumTasksControl;
}
