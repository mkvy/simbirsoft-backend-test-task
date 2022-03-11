package com.notes.notesappback.controller;


import com.notes.notesappback.dto.UserDto;
import com.notes.notesappback.dto.UserRegDto;
import com.notes.notesappback.exceptions.UserExistsException;
import com.notes.notesappback.model.User;
import com.notes.notesappback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class UserController {
    private final UserService userService;

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
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserRegDto userRegDto,
                                            HttpServletRequest request,
                                            Errors errors) {
        ModelAndView mav = new ModelAndView("/auth/registration");
        UserDto userDto = UserDto.builder().username(userRegDto.getUsername()).password(userRegDto.getPassword()).build();
        try {
            User registered = userService.registerUser(userDto);
        } catch (UserExistsException uaeEx) {
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }
        return new ModelAndView("/index");
    }

}
