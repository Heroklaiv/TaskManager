package com.example.Owl.s.Heart.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexPageDTO {
    String thisDay;
    String nextDay;
    String nextWeek;
    TaskListDayDTO tasksPerformThisDay;
    TaskListDayDTO tasksControlThisDay;
    TaskListDayDTO tasksPerformThisDayBeforeDeadLine;
    TaskListDayDTO tasksControlThisDayBeforeDeadLine;
    TaskListDayDTO tasksPerformNextDay;
    TaskListDayDTO tasksControlNextDay;
    TaskListDayDTO tasksPerformNextWeek;
    TaskListDayDTO tasksControlNextWeek;
}
