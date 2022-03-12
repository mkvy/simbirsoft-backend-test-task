package com.notes.notesappback.controller;


import com.notes.notesappback.dto.UserDto;
import com.notes.notesappback.dto.UserRegDto;
import com.notes.notesappback.exceptions.UserExistsException;
import com.notes.notesappback.model.User;
import com.notes.notesappback.service.AuthService;
import com.notes.notesappback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("/register")
    public ModelAndView registerUserAccount(@Valid @ModelAttribute("user") UserRegDto userRegDto,
                                            BindingResult result) {
        ModelAndView mav = new ModelAndView("/auth/registration");
        if (result.hasErrors()) {
            return mav;
        }
        UserDto userDto = UserDto.builder().username(userRegDto.getUsername()).password(userRegDto.getPassword()).build();
        try {
            User registered = userService.registerUser(userDto);
            authService.login(userDto);
        } catch (UserExistsException uaeEx) {
            log.info("Controller UserExistsException handle");
            mav = new ModelAndView("/error");
            mav.addObject("errorMessage", "An account for that username/email already exists.");
            mav.setStatus(HttpStatus.CONFLICT);
            return mav;
        }
        return new ModelAndView("redirect:/");
    }

}
