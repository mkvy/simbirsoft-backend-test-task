package com.notes.notesappback.service;

import com.notes.notesappback.dto.NoteDto;
import com.notes.notesappback.model.Note;
import com.notes.notesappback.model.User;
import com.notes.notesappback.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public Note saveNoteFromCurrentUser(NoteDto noteDto) {
        User user = userService.getCurrentUser();
        return noteRepository.save(Note.builder().noteText(noteDto.getNoteText()).user(user).createdDate(Instant.now()).build());
    }

    public Long countNote() {
        User user = userService.getCurrentUser();
        return noteRepository.countAllByUser(user);
    }

    public List<Note> findAllNotesByCurUser() {
        log.info("Fetching notes from current user");
        User user = userService.getCurrentUser();
        return noteRepository.findAllByUser(user);
    }
}
