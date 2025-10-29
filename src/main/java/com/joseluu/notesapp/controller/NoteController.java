package com.joseluu.notesapp.controller;

import com.joseluu.notesapp.model.Notes;
import com.joseluu.notesapp.exception.ConcurrencyConflictException;
import com.joseluu.notesapp.exception.NoteNotFoundException;
import com.joseluu.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public List<Notes> getAllNotes() {
        return noteRepository.findAll();
    }


    @PostMapping
    public Notes createNote(@RequestBody Notes note) {

        return noteRepository.save(note);
    }

    @GetMapping("/{id}")
    public Notes getNoteById(@PathVariable Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        if (!noteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public Notes updateNote(@PathVariable Long id, @Validated @RequestBody Notes noteDetails) {
        Notes note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id)); // Reutiliza la excepción del Nivel 1
        if ("CONFLICTO".equalsIgnoreCase(noteDetails.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        return noteRepository.save(note);
    }
}