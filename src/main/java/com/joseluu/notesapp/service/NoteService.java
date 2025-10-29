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
    private final NoteRepository noteRepository;
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    public List<Notes> findAll() {
        return noteRepository.findAll();
    }
    public Notes findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }
    public Notes save(Notes note) {
        return noteRepository.save(note);
    }
    public void deleteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }
    public Notes update(Long id, Notes noteDetails) {
        Notes existingNote = findById(id);

        if ("CONFLICTO".equalsIgnoreCase(noteDetails.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }

        existingNote.setTitle(noteDetails.getTitle());
        existingNote.setContent(noteDetails.getContent());

        return noteRepository.save(existingNote);
    }


}
