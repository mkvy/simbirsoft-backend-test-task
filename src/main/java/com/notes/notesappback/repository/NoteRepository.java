package com.notes.notesappback.repository;

import com.notes.notesappback.model.Note;
import com.notes.notesappback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUser(User user);
    Long countAllByUser(User user);
}
