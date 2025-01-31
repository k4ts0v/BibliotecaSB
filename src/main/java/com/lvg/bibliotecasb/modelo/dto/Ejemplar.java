package com.lvg.bibliotecasb.modelo.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    @ColumnDefault("'Disponible'")
    @Lob
    @Column(name = "estado")
    @NotBlank
    @Pattern(regexp = "(Disponible|Prestado|Dañado)", message = "El estado solo puede ser 'Disponible', 'Prestado' o 'Dañado'")
    private String estado;

    @NotNull
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "isbn", nullable = false)
    @JsonIncludeProperties("isbn")
    private Libro isbn;

    @OneToMany(mappedBy = "ejemplar")
    @JsonIncludeProperties("id")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();
}