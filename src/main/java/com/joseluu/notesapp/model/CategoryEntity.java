package com.joseluu.notesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // Colección inversa: una categoría tiene muchas notas
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<NotesEntity> notes = new HashSet<>();

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NotesEntity> getNotes() {
        return notes;
    }

    public void setNotes(Set<NotesEntity> notes) {
        this.notes = notes;
    }

    // Método helper opcional para añadir nota
    public void addNote(NotesEntity note) {
        notes.add(note);
        note.setCategoria(this);
    }

    // Método helper opcional para eliminar nota
    public void removeNote(NotesEntity note) {
        notes.remove(note);
        note.setCategoria(null);
    }
}
