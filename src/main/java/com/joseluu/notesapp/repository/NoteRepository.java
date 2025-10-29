package com.joseluu.notesapp.repository;

import com.joseluu.notesapp.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Notes, Long> {

}
