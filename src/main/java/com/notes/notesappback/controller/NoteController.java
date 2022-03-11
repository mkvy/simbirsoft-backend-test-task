package com.notes.notesappback.controller;

import com.notes.notesappback.dto.NoteDto;
import com.notes.notesappback.model.Note;
import com.notes.notesappback.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/saveNote")
    public ResponseEntity<?> saveNote(@RequestBody NoteDto noteDto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/note/saveNote").toUriString());
        return ResponseEntity.created(uri).body(noteService.saveNoteFromCurrentUser(noteDto));
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
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok().body(noteService.findAllNotes());
    }
}
