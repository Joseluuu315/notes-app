package com.joseluu.notesapp.repository;

import com.joseluu.notesapp.model.NotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NotesEntity, Long> {

    List<NotesEntity> findByTitleContainingIgnoreCase(String keyword);

}
