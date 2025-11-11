package com.joseluu.notesapp.repository;

import com.joseluu.notesapp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Notes, Long> {

    List<Notes> findByTitleContainingIgnoreCase(String keyword);

}
