package com.notes.notesappback.repository;

import com.notes.notesappback.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
