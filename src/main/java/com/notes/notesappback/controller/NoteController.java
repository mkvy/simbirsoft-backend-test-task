package com.notes.notesappback.controller;

import com.notes.notesappback.dto.NoteDto;
import com.notes.notesappback.model.Note;
import com.notes.notesappback.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "/saveNote", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveNote(@Valid NoteDto noteDto) {
        log.info("Recieved note  " + noteDto.getNoteText());
        noteService.saveNoteFromCurrentUser(noteDto);
        return "redirect:/api/notes";
    }

    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/note/create").toUriString());
        return ResponseEntity.created(uri).body(noteService.createNote(note));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        return ResponseEntity.ok().body(noteService.findNoteById(id));
    }

    @GetMapping()
    public String getAllNotesByCurUser(Model model) {
        model.addAttribute("notes", noteService.findAllNotesByCurUser());
        return "/note/list";
    }
    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok().body(noteService.findAllNotes());
    }
}
