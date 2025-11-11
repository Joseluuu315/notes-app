package com.joseluu.notesapp.service;

import com.joseluu.notesapp.model.Notes;
import com.joseluu.notesapp.exception.ConcurrencyConflictException;
import com.joseluu.notesapp.exception.NoteNotFoundException;
import com.joseluu.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio para las operaciones CRUD de las notas.
 * Se comunica con el repositorio {@link NoteRepository} para acceder a la base de datos.
 */
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Constructor para inyectar la dependencia del repositorio.
     *
     * @param noteRepository el repositorio que gestiona las entidades de tipo {@link Notes}.
     */
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Obtiene todas las notas almacenadas en la base de datos.
     *
     * @return una lista de todas las notas.
     */
    public List<Notes> findAll() {
        return noteRepository.findAll();
    }

    /**
     * Busca notas que contengan un texto específico dentro de su contenido.
     * Realiza un filtrado manual sobre todas las notas existentes.
     *
     * @param content texto que se buscará dentro del contenido de las notas.
     * @return lista de notas cuyo contenido incluye el texto indicado.
     */
    public List<Notes> findAllByContent(String content) {
        List<Notes> notes = noteRepository.findAll();
        List<Notes> filteredNotes = new ArrayList<>();

        for (Notes note : notes) {
            if (note.getContent().contains(content)) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    /**
     * Busca notas cuyo título contenga una palabra clave, sin importar mayúsculas o minúsculas.
     *
     * @param keyword palabra clave a buscar en el título.
     * @return lista de notas que coinciden con la palabra clave o todas las notas si el parámetro es nulo o vacío.
     */
    public List<Notes> findByTitleKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return noteRepository.findAll();
        }
        return noteRepository.findByTitleContainingIgnoreCase(keyword);
    }

    /**
     * Busca una nota específica por su ID.
     *
     * @param id identificador único de la nota.
     * @return la nota encontrada.
     * @throws NoteNotFoundException si no se encuentra una nota con el ID indicado.
     */
    public Notes findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    /**
     * Guarda una nueva nota en la base de datos.
     * Lanza una excepción si el contenido de la nota es "DUPLICADO".
     *
     * @param note la nota a guardar.
     * @return la nota guardada.
     * @throws ConcurrencyConflictException si el contenido es "DUPLICADO".
     */
    public Notes saveNotes(Notes note) {
        if ("DUPLICADO".equalsIgnoreCase(note.getContent())) {
            throw new ConcurrencyConflictException("Nota ID " + note.getId());
        }

        return noteRepository.save(note);
    }

    /**
     * Elimina una nota por su ID.
     *
     * @param id identificador único de la nota a eliminar.
     * @throws NoteNotFoundException si la nota no existe.
     */
    public void deleteNoteById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }

    /**
     * Actualiza el título y contenido de una nota existente.
     * Lanza una excepción si el nuevo contenido es "CONFLICTO".
     *
     * @param id           identificador de la nota a actualizar.
     * @param noteDetails  objeto {@link Notes} con los nuevos datos.
     * @return la nota actualizada.
     * @throws NoteNotFoundException       si la nota no existe.
     * @throws ConcurrencyConflictException si el contenido es "CONFLICTO".
     */
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

    public void orderByDate(){
        List<Notes> notes = noteRepository.findAll();

        notes.sort(new Comparator<Notes>() {
            @Override
            public int compare(Notes o1, Notes o2) {

                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
    }
}
