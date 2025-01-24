package com.lvg.bibliotecasb.modelo.repositories;

import com.lvg.bibliotecasb.modelo.dto.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrosRepository extends JpaRepository<Libro, Long> {
    Libro findLibroByIsbn(String isbn);
    void deleteLibroByIsbn(String isbn);
}