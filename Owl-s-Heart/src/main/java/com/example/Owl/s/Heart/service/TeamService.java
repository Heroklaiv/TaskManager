package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.dto.CreateTaskDTO;
import com.example.Owl.s.Heart.dto.TeamPageDTO;
import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import com.example.Owl.s.Heart.repository.AccountRepository;
import com.example.Owl.s.Heart.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    teamIds.add(team.getId());
                }
            }
        }
        return teamIds;
    }

    public CreateTaskDTO createListTeamWhenAccountAdmin(Account account) {
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        Optional<List<Team>> optionalTeams = teamRepository.findAllByAdmin(account);
        if (optionalTeams.isPresent()) {
            List<Team> teams = optionalTeams.get();
            createTaskDTO.setTeamListWhenAccountAdmin(teams);
        } else return createTaskDTO;
        return createTaskDTO;
    }

    public Team createNewTeam(Account account, String name) {
        Team team = new Team();
        team.setName(name);
        team.setAdmin(account);
        return teamRepository.save(team);
    }

    public Team getTeamById(long id) {
        return teamRepository.findById(id).get();
    }

    public void updateTeamName(Long idTeam, String name) {
        Team team = teamRepository.findById(idTeam).get();
        team.setName(name);
        teamRepository.save(team);
    }

    public void updateTeamMembers(Long idTeam, String username) {
        Team team = teamRepository.findById(idTeam).get();
        Account account = accountRepository.findByUsername(username).get();
        if(team.getMember()== null){
            List<Account> accountList = new ArrayList<>();
            accountList.add(account);
            team.setMember(accountList);
        }else {
            team.getMember().add(account);
        }
        teamRepository.save(team);
    }

    public List<Team> getTeamsByMember(Account account) {
        return teamRepository.findByMemberContains(account).get();
    }


}
