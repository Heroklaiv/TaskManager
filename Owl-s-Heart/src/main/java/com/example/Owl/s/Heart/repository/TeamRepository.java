package com.example.Owl.s.Heart.repository;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    /* Optional<Team> findById(Long id);*/
    Optional<Team> findByName(String name);

    Optional<List<Team>> findAllByAdmin(Account account);

    Optional<List<Team>> findByMemberContains(Account member);

    @Query("SELECT t FROM Team t JOIN t.member m WHERE m.id = :memberId")
    Optional<List<Team>> findTeamsByMemberId(@Param("memberId") Long memberId);

}