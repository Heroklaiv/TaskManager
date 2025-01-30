package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Team;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TeamPageDTO {
   List<Team> teamListWhenAccountAdmin;
   List<Team> teamListWhenAccountMembers;
}
