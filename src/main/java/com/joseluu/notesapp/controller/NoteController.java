package com.joseluu.notesapp.controller;

import com.joseluu.notesapp.model.Notes;
import com.joseluu.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Notes> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping
    public Notes createNote(@RequestBody Notes note) {
        return noteService.saveNotes(note);
    }

    @GetMapping("/{id}")
    public Notes getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Notes updateNote(@PathVariable Long id, @Validated @RequestBody Notes noteDetails) {
        return noteService.updateNote(id, noteDetails);
    }
}