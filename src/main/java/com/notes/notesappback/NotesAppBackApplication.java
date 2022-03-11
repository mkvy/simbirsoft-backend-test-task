package com.notes.notesappback;

import com.notes.notesappback.model.Note;
import com.notes.notesappback.model.User;
import com.notes.notesappback.service.NoteService;
import com.notes.notesappback.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class NotesAppBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesAppBackApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, NoteService noteService) {
        return args -> {
            userService.saveUser(new User(null, "asd@gmail.com", "asd", new ArrayList<>()));
            userService.saveUser(new User(null, "asd1@gmail.com", "asd1", new ArrayList<>()));
            noteService.createNote(Note.builder().user(userService.getUser("asd@gmail.com")).noteText("First note").build());
        };
    }
}
