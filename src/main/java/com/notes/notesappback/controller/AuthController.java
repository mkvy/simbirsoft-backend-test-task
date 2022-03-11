package com.notes.notesappback.controller;

import com.notes.notesappback.dto.UserDto;
import com.notes.notesappback.dto.UserRegDto;
import com.notes.notesappback.model.User;
import com.notes.notesappback.service.AuthService;
import com.notes.notesappback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody UserDto userDto) {
        log.info("login " + userDto.getUsername() + " " + userDto.getPassword());
        authService.login(userDto);
        log.info("logged in " + userService.getCurrentUser().getUsername());
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }

    @GetMapping("/signup")
    public String signupPage(WebRequest request, Model model) {
        UserRegDto userRegDto = new UserRegDto();
        model.addAttribute("user", userRegDto);
        return "/auth/registration";
    }

}
