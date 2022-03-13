package com.notes.notesappback.controller;

import com.notes.notesappback.dto.NoteDto;
import com.notes.notesappback.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @PostMapping(value = "/saveNote", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveNote(@Valid NoteDto noteDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/api/createNote?error=true";
        }
        log.info("Recieved note  " + noteDto.getNoteText());
        noteService.saveNoteFromCurrentUser(noteDto);
        return "redirect:/api/notes";
    }

    @GetMapping()
    public String getAllNotesByCurUser(Model model) {
        model.addAttribute("notes", noteService.findAllNotesByCurUser());
        return "note/list";
    }
}
