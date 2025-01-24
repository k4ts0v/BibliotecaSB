package com.lvg.bibliotecasb.modelo.repositories;

import com.lvg.bibliotecasb.modelo.dto.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamosRepository extends JpaRepository<Prestamo, Long> {
    Prestamo getPrestamoById(Long id);
    void deletePrestamoById(Long id);
}
