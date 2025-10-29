package com.joseluu.notesapp.controller;

import java.util.List;

import com.joseluu.notesapp.model.Notes;
import com.joseluu.notesapp.exception.ConcurrencyConflictException;
import com.joseluu.notesapp.exception.NoteNotFoundException;
import com.joseluu.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class PageController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/new-note")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Notes());
        return "new_note";
    }

    @GetMapping("/list-notes")
    public String showAllNotes(Model model) {
        List<Notes> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "list_notes";
    }
    @GetMapping("/list-notes-important")
    public String showImportantNote(Model model) {
        List<Notes> notes = noteRepository.findAll();

        for (Notes note : notes) {
            if (note.getContent().contains("importante")){
                model.addAttribute("notes", notes);
            }
        }

        return "list_notes_important";
    }

    @GetMapping("/delete-note")
    public String showDeleteNotePage() {
        return "delete_notes";
    }

    @PostMapping("/notes-form")
    public String createNote(@Validated Notes note, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("note", note);
            return "new_note";
        }
        noteRepository.save(note);
        return "redirect:/list_notes";
    }


    @GetMapping("/test-500")
    public String triggerInternalError() {
        String s = null;
        s.length();
        return "menu";
    }

    @GetMapping("/edit-note/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Notes note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id)); // Reutiliza el 404
        model.addAttribute("note", note);
        return "edit_note";
    }

    @PutMapping("/edit-note/{id}")
    public String updateNoteMvc(@PathVariable Long id, @Validated Notes note, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("note", note);
            return "edit_note";
        }

        Notes existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if ("CONFLICTO".equalsIgnoreCase(existingNote.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + id);
        }


        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
        return "redirect:/list_notes";

    }

}