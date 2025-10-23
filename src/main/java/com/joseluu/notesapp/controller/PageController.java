package com.joseluu.notesapp.controller;


import java.util.List;

import com.joseluu.notesapp.entity.Notes;
import com.joseluu.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @Autowired
    private NoteRepository noteRepository; // Necesario para listar notas [cite: 86, 87]

    @GetMapping("/menu")
    public String showMenu() {
        return "menu"; // Devuelve menu.html [cite: 147, 150]
    }

    @GetMapping("/new-note")
    public String showNewNoteForm(Model model) {
        // Añade un objeto Note vacío al modelo para que el formulario lo use [cite: 31, 33]
        model.addAttribute("note", new Notes());
        return "new_note"; // Devuelve new_note.html [cite: 34]
    }

    @GetMapping("/list-notes")
    public String showAllNotes(Model model) {
        // Carga todas las notas de la BBDD [cite: 94]
        List<Notes> notes = noteRepository.findAll();
        model.addAttribute("notes", notes); // Añade la lista al modelo [cite: 95]
        return "list_notes"; // Devuelve list_notes.html [cite: 97]
    }

    @GetMapping("/delete-note")
    public String showDeleteNotePage() {
        // Simplemente muestra la página con el formulario JS
        return "delete_note"; // Devuelve delete_note.html [cite: 157, 160]
    }
}