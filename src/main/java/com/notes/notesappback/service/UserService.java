package com.notes.notesappback.service;

import com.notes.notesappback.dto.UserDto;
import com.notes.notesappback.exceptions.UserExistsException;
import com.notes.notesappback.model.User;
import com.notes.notesappback.repository.UserRepository;
import com.notes.notesappback.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public User saveUser(User user) {
        log.info("Saving new user {} to the db", user.getUsername());
        String password = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User registerUser(UserDto userDto) {
        if (emailExist(userDto.getUsername())) {
            log.info("UserExistsException occused");
            throw new UserExistsException("There is already account with that email: " + userDto.getUsername());
        }
        log.info("Signup new user {} to the db", userDto.getUsername());
        String password = securityConfig.passwordEncoder().encode(userDto.getPassword());
        User user = new User(null, userDto.getUsername(), password, new ArrayList<>());
        return userRepository.save(user);
    }


    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with name not found: " + username));
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
    private boolean emailExist(String email) {
        return userRepository.getByUsername(email) != null;
    }

    public User getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
