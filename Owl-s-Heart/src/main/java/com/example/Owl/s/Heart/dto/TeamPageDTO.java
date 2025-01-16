package com.example.Owl.s.Heart.dto;

import com.example.Owl.s.Heart.entity.Team;
import lombok.Data;

import java.util.List;

@Data
public class TeamPageDTO {
   List<Team> teamListWhenAccountAdmin;
   List<Team> teamListWhenAccountMembers;
}
