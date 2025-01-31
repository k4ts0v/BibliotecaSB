package com.lvg.bibliotecasb.modelo.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
public class Libro {
    @Id
    @Size(max = 20)
    @Column(name = "isbn", nullable = false, length = 20)
    @NotBlank(message = "El campo no puede estar vacío.")
    @Pattern(regexp= "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "El campo debe tener un formato válido.")
    private String isbn;

    @Size(max = 200)
    @NotNull
    @Column(name = "titulo", nullable = false, length = 200)
    @Pattern(regexp = "[A-z0-9\\s]{1,200}", message = "Solo puede contener caracteres alfanuméricos, y como máximo, 200 caracteres.")
    private String titulo;

    @Size(max = 100)
    @NotNull
    @Pattern(regexp = "[A-z0-9\\s]{1,100}", message = "Solo puede contener caracteres alfanuméricos, y como máximo, 100 caracteres.")
    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

    @OneToMany(mappedBy = "isbn", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // This is the "managed" side of the relationship
    private Set<Ejemplar> ejemplares = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}