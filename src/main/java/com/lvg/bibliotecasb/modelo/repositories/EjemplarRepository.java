package com.lvg.bibliotecasb.modelo.repositories;

import com.lvg.bibliotecasb.modelo.dto.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
    Ejemplar findEjemplarById(Integer id);
    void deleteEjemplarById(Integer id);
}
