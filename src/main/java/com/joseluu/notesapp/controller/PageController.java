package com.joseluu.notesapp.controller;

import com.joseluu.notesapp.model.CategoryEntity;
import com.joseluu.notesapp.model.NotesEntity;
import com.joseluu.notesapp.repository.CategoryRepository;
import com.joseluu.notesapp.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PageController {

    private final NoteService noteService;
    private final CategoryRepository categoriaRepository;

    public PageController(NoteService noteService, CategoryRepository categoriaRepository) {
        this.noteService = noteService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/")
    public String showMenu() {
        return "menu";
    }

    @GetMapping("/error-500")
    public String error500() {
        throw new RuntimeException("Error simulado");
    }


    @GetMapping("/new-note")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new NotesEntity());

        // Pasar todas las categorías al modelo
        List<CategoryEntity> allCategorias = categoriaRepository.findAll();
        model.addAttribute("allCategorias", allCategorias);

        return "new_note";
    }

    @PostMapping("/create-note")
    public String createNote(@Validated NotesEntity note, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Si hay errores, debemos volver a pasar las categorías
            model.addAttribute("allCategorias", categoriaRepository.findAll());
            return "new_note";
        }

        noteService.saveNotes(note);
        return "redirect:/list-notes";
    }

    @GetMapping("/edit-note/{id}")
    public String showEditNoteForm(@PathVariable("id") Long id, Model model) {
        NotesEntity note = noteService.findById(id);
        model.addAttribute("note", note);

        // Pasar todas las categorías al modelo
        List<CategoryEntity> allCategorias = categoriaRepository.findAll();
        model.addAttribute("allCategorias", allCategorias);

        return "edit_note";
    }

    @PostMapping("/edit-note/{id}")
    public String updateNote(@PathVariable("id") Long id, @Validated NotesEntity note, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allCategorias", categoriaRepository.findAll());
            return "edit_note";
        }

        noteService.updateNote(id, note);
        return "redirect:/list-notes";
    }

    @GetMapping("/list-notes")
    public String showAllNotes(Model model) {
        List<NotesEntity> notes = noteService.findAll();

        noteService.orderByDate(notes);
        model.addAttribute("notes", notes);
        model.addAttribute("totalNotes", notes.size());
        model.addAttribute("categoria");

        return "list_notes";
    }

    @GetMapping("/list-notes-search")
    public String showAllNotes(@RequestParam(required = false) String keyword, Model model) {
        List<NotesEntity> notes = noteService.findByTitleKeyword(keyword);

        model.addAttribute("notes", notes);
        model.addAttribute("totalNotes", notes.size());
        model.addAttribute("keyword", keyword);

        return "list_notes_search";
    }

    @GetMapping("/list-notes-important")
    public String showAllNotesImportant(Model model) {
        List<NotesEntity> notes = noteService.findAllByContent("important");

        model.addAttribute("importantNotes", notes);

        return "list_notes_important";
    }


    @GetMapping("/delete-note-in-list/{id}")
    public String deleteNoteInList(@PathVariable("id") Long id) {
        NotesEntity note = noteService.findById(id);

        noteService.deleteNoteById(id);

        return "redirect:/list-notes";
    }

    @GetMapping("/delete-note")
    public String showDeleteNotePage() {
        return "delete_notes";
    }
}