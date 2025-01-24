package com.lvg.bibliotecasb.modelo.repositories;

import com.lvg.bibliotecasb.modelo.dto.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
    Usuario findUsuarioById(Integer id);
    void deleteUsuarioById(Integer id);
    boolean existsUsuarioById(Usuario usuario);
}
