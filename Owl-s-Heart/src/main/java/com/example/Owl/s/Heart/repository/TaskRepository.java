package com.example.Owl.s.Heart.repository;


import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Task;
import com.example.Owl.s.Heart.entity.Team;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @NotNull Optional<Task> findById(@NotNull Long id);

    @Transactional
    void deleteById(@NotNull Long id);

    Optional<List<Task>> findTaskByDeadlineAndOwner(LocalDate deadline, Account owner);

    Optional<List<Task>> findTaskByOwnerAndDeadlineBefore(Account owner, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndDeadline(Team workTeam, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersIsNullAndDeadline(Team workTeam, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersIsNotNullAndDeadline(Team workTeam, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersAndDeadline(Team workTeam, Account performers, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersAndDeadlineBefore(Team workTeam, Account performers, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersIsNullAndDeadlineBefore(Team workTeam, LocalDate deadline);

    Optional<List<Task>> findByWorkTeamAndPerformersIsNotNullAndDeadlineBefore(Team workTeam, LocalDate deadline);

    Optional<List<Task>> findTaskByDeadlineAndPerformers(LocalDate deadline, Account performer);

    Optional<List<Task>> findTaskByOwnerAndPerformersIsNullAndDeadline(Account owner, LocalDate deadline);

    Optional<List<Task>> findTaskByOwnerAndPerformersIsNotNullAndDeadline(Account owner, LocalDate deadline);

    Optional<List<Task>> findTaskByOwnerAndPerformersIsNullAndDeadlineBefore(Account owner, LocalDate deadline);

    Optional<List<Task>> findTaskByOwnerAndPerformersIsNotNullAndDeadlineBefore(Account owner, LocalDate deadline);

    Optional<List<Task>> findTaskByWorkTeamAndDeadlineBefore(Team team, LocalDate deadline);
}
