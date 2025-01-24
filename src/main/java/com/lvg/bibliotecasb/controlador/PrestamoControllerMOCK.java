package com.lvg.bibliotecasb.controlador;

import com.lvg.bibliotecasb.modelo.dto.Prestamo;
import com.lvg.bibliotecasb.modelo.dto.Usuario;
import com.lvg.bibliotecasb.modelo.repositories.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoControllerMOCK {
    private EjemplarControllerMOCK ejemplarControllerMOCK;
    private UsuarioControllerMOCK usuarioControllerMOCK;
    PrestamosRepository prestamosRepository;

    // Constructor vacío del controlador.
    public PrestamoControllerMOCK() {}

    // Constructor del controlador.
    @Autowired
    public PrestamoControllerMOCK(PrestamosRepository prestamosRepository, EjemplarControllerMOCK ejemplarControllerMOCK, UsuarioControllerMOCK usuarioControllerMOCK) {
        this.prestamosRepository = prestamosRepository;
        this.ejemplarControllerMOCK = ejemplarControllerMOCK;
        this.usuarioControllerMOCK = usuarioControllerMOCK;
    }

    // GET. Hace SELECT * en la bbdd.
    @GetMapping
    public ResponseEntity<List<Prestamo>> getPrestamos() {
        List<Prestamo> prestamos = prestamosRepository.findAll();
        return ResponseEntity.ok(prestamos);
    }

    // GET con id. Hace SELECT por ID en la bbdd.
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamo(@PathVariable Long id) {
        Prestamo prestamo = prestamosRepository.findById(id).orElse(null);
        return ResponseEntity.ok(prestamo);
    }

    // POST. Hace INSERT en la bbdd.
    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> addPrestamo(@RequestBody Prestamo prestamo) {
        if (prestamo != null && ejemplarControllerMOCK.exists(prestamo.getEjemplar()) && usuarioControllerMOCK.exists(prestamo.getUsuario())) {
            Prestamo prestamoSave = prestamosRepository.save(prestamo);
            return ResponseEntity.created(null).body(prestamoSave);
        }
        return ResponseEntity.badRequest().build();
    }

    //    PUT. Hace UPDATE en la bbdd.
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@RequestBody Prestamo prestamo) {
        if (prestamo != null && ejemplarControllerMOCK.exists(prestamo.getEjemplar()) && usuarioControllerMOCK.exists(prestamo.getUsuario())) {
            Prestamo prestamoSave = prestamosRepository.save(prestamo);
            return ResponseEntity.ok(prestamoSave);
        }
        return ResponseEntity.badRequest().build();
    }

    //    DELETE por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrestamo(@PathVariable Long id) {
        Prestamo prestamo = prestamosRepository.findById(id).orElse(null);
        if (prestamo != null) {
            prestamosRepository.delete(prestamo);
            return ResponseEntity.ok().body(String.format("Préstamo %s eliminado.", prestamo));
        }
        return ResponseEntity.badRequest().build();
    }
}
