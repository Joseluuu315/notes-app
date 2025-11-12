package com.joseluu.notesapp.controller;

import com.joseluu.notesapp.model.NotesEntity;
import com.joseluu.notesapp.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping
    public List<NotesEntity> getAllNotes() {
        return noteService.findAll();
    }

    @PostMapping
    public NotesEntity createNote(@RequestBody NotesEntity note) {
        return noteService.saveNotes(note);
    }

    @GetMapping("/{id}")
    public NotesEntity getNoteById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @GetMapping("/search")
    public List<NotesEntity> findNoteByTitle(@RequestParam String title) {
        return noteService.findByTitleKeyword(title);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public NotesEntity updateNote(@PathVariable Long id, @Validated @RequestBody NotesEntity noteDetails) {
        return noteService.updateNote(id, noteDetails);
    }
}