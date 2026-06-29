package com.narcis.devpath.noteservice.repository;

import com.narcis.devpath.noteservice.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note, Long> {
}
