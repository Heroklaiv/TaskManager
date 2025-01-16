package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.dto.TeamPageDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.repository.AccountRepository;
import com.example.Owl.s.Heart.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private AccountRepository accountRepository;


    private List<Team> getTeamByIdAdmin(long id) {
        Account account = accountRepository.findById(id).get();
        return teamRepository.findAllByAdmin(account).get();
    }

    public TeamPageDTO createPageTeam(Account account) {
        TeamPageDTO teamPageDTO = new TeamPageDTO();
        List<Team> teamListMember = new ArrayList<>();
        List<Team> teamList = teamRepository.findAll();
        List<Long> TeamIdList = searchTeamWhenAccountMember(teamList, account);
        for (Long TeamId : TeamIdList) {
           teamListMember.add(teamRepository.findById(TeamId).get());
        }
        teamPageDTO.setTeamListWhenAccountMembers(teamListMember);
        teamPageDTO.setTeamListWhenAccountAdmin(getTeamByIdAdmin(account.getId()));
        return teamPageDTO;
    }

    private List<Long> searchTeamWhenAccountMember(List<Team> teamList, Account account) {
        List<Long> teamIds = new ArrayList<>();
        for (Team team : teamList) {
            List<Account> accountList = team.getMember();
            for (Account ac : accountList) {
                if (ac.getId() == account.getId()) {
                    teamIds.add(ac.getId());
                }
            }
        }
        return teamIds;
    }


}
