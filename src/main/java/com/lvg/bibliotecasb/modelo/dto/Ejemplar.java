package com.lvg.bibliotecasb.modelo.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ejemplar")
@Data
@NoArgsConstructor
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "estado", nullable = false)
    @NotBlank(message = "El estado no puede estar vacío.")
    @Pattern(regexp = "(Disponible|Prestado|Dañado)", message = "El estado solo puede ser 'Disponible', 'Prestado' o 'Dañado'")
    private String estado = "Disponible"; // Default value in Java

    @NotNull(message = "El libro no puede ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    @JsonBackReference // This is the "back" side of the relationship
    private Libro isbn;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Prestamo> prestamos = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ejemplar ejemplar = (Ejemplar) o;
        return Objects.equals(id, ejemplar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}