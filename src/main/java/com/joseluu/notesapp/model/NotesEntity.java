package com.joseluu.notesapp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Entity
public class NotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Check(constraints = "LENGTH(title) >= 3")
    private String title;

    private String content;
    private LocalDate createdAt;
    private HttpStatus status;

    // Nueva relación N:1 con Categoria
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private CategoryEntity categoria;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public CategoryEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoryEntity categoria) {
        this.categoria = categoria;
    }
}
