package com.notes.notesappback.service;

import com.notes.notesappback.dto.NoteDto;
import com.notes.notesappback.exceptions.NoteNotFoundException;
import com.notes.notesappback.model.Note;
import com.notes.notesappback.model.User;
import com.notes.notesappback.repository.NoteRepository;
import com.notes.notesappback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserRepository userRepository;
    private final UserService userService;

    public Note createNote(Note note) {
        log.info("Saving note from user " + note.getUser().getUsername());
        return noteRepository.save(note);
    }

    public Note saveNoteFromCurrentUser(NoteDto noteDto) {
        User user = userService.getCurrentUser();
        return noteRepository.save(Note.builder().noteText(noteDto.getNoteText()).user(user).createdDate(Instant.now()).build());
    }

    public void deleteNote(Note note) {
        log.info("Deleting note {}", note.getId());
        noteRepository.delete(note);
    }

    public Long countNote() {
        User user = userService.getCurrentUser();
        return noteRepository.countAllByUser(user);
    }

    public Note addNoteFromUser(String noteText, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name not found: " + username));
        Note note = Note.builder().noteText(noteText).user(user).build();
        return noteRepository.save(note);
    }

    public Note findNoteById(Long id) {
        log.info("Fetching note {}", id);
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Not found note with id " + id.toString()));
    }

    public List<Note> findAllNotes() {
        log.info("Fetching all notes");
        return noteRepository.findAll();
    }

    public List<Note> findAllNotesByCurUser() {
        log.info("Fetching notes from current user");
        User user = userService.getCurrentUser();
        return noteRepository.findAllByUser(user);
    }
}
