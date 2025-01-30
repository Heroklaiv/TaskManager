package com.example.Owl.s.Heart.repository;

import com.example.Owl.s.Heart.entity.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @NotNull Optional<Account> findById(@NotNull Long id);
    Optional<Account> findByUsername(String username);
}
