package com.joseluu.notesapp.service;

import com.joseluu.notesapp.model.Notes;
import com.joseluu.notesapp.exception.ConcurrencyConflictException;
import com.joseluu.notesapp.exception.NoteNotFoundException;
import com.joseluu.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public List<Notes> findAll() {
        return noteRepository.findAll();
    }

    public Notes findById(Long id) {
        return noteRepository.findById(id).orElseThrow(()
                -> new NoteNotFoundException(id));
    }

    public List<Notes> getAllNotes() {
        return noteRepository.findAll();
    }

    public Notes saveNotes(Notes note) {
        return noteRepository.save(note);
    }



    public Notes getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public void deleteNoteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }

    public Notes updateNote(Long id, Notes noteDetails) {
        Notes note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if ("CONFLICTO".equalsIgnoreCase(noteDetails.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        return noteRepository.save(note);
    }
}
