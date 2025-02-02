package com.example.Owl.s.Heart.service;

import com.example.Owl.s.Heart.entity.Account;
import com.example.Owl.s.Heart.entity.Role;
import com.example.Owl.s.Heart.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testCreateAccount() {
        // Arrange
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("password");
        account.getAuthorities().add(Role.ROLE_USER);

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        accountService.create(account);

        // Assert
        verify(passwordEncoder, times(1)).encode("password");
        verify(accountRepository, times(1)).save(account);
        assertTrue(account.getAuthorities().contains(Role.ROLE_USER));
        assertEquals("encodedPassword", account.getPassword());
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("password");

        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.of(account));

        // Act
        UserDetails userDetails = accountService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(accountRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername("unknownUser");
        });
    }

    @Test
    void testFindByUsername_Success() {
        // Arrange
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("password");

        when(accountRepository.findByUsername("testUser")).thenReturn(Optional.of(account));

        // Act
        Account foundAccount = accountService.findByUsername("testUser");

        // Assert
        assertNotNull(foundAccount);
        assertEquals("testUser", foundAccount.getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        // Arrange
        when(accountRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        // Act
        Account foundAccount = accountService.findByUsername("unknownUser");

        // Assert
        assertNull(foundAccount);
    }
}
