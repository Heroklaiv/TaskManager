package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Role;
import com.example.Owl.s.Heart.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(Account account) {
        account.getAuthorities().add(Role.ROLE_USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byUsername = accountRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new UsernameNotFoundException(username);
    }
    public Account findByUsername(String username) {
       Optional<Account> accountOptional = accountRepository.findByUsername(username);
       if (accountOptional.isPresent()) {
           return accountOptional.get();
       }
       else return null;
    }
    private List<Account> findAll() {
        return accountRepository.findAll();
    }
}
