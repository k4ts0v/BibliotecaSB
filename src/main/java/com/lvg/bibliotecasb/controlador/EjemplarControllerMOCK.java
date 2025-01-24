package com.lvg.bibliotecasb.controlador;

import com.lvg.bibliotecasb.modelo.dto.Ejemplar;
import com.lvg.bibliotecasb.modelo.dto.Libro;
import com.lvg.bibliotecasb.modelo.dto.Usuario;
import com.lvg.bibliotecasb.modelo.repositories.EjemplarRepository;
import jakarta.validation.Valid;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ejemplares")

public class EjemplarControllerMOCK {
    static EjemplarRepository repositorioEjemplar;
    LibroControllerMOCK libroControllerMOCK;

    // Constructor vacío del controlador.
    public EjemplarControllerMOCK(){}

    // Constructor del controlador.
    @Autowired
    public EjemplarControllerMOCK(EjemplarRepository repositorioEjemplar, LibroControllerMOCK libroControllerMOCK){
        this.repositorioEjemplar = repositorioEjemplar;
        this.libroControllerMOCK = libroControllerMOCK;
    }

    // Método que comprueba si el ejemplar existe en la base de datos.
    public boolean exists(Ejemplar ejemplar) {
        boolean resultado;
        return resultado = repositorioEjemplar.findEjemplarById(ejemplar.getId()) != null;
    }

    //GET --> SELECT *
    @GetMapping
    public ResponseEntity<List<Ejemplar>> getEjemplar(){
        List<Ejemplar> lista = this.repositorioEjemplar.findAll();
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //GET BY ID --> SELECT BY ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id){
            Ejemplar l = this.repositorioEjemplar.findEjemplarById(id);
            return ResponseEntity.ok(l);
    }

   //POST --> INSERT
    @PostMapping("/ejemplar")
    public ResponseEntity<Ejemplar> addEjemplar(@Valid @RequestBody Ejemplar ejemplar) {
        if (libroControllerMOCK.exists(ejemplar.getIsbn())) {
            System.out.println("Entra aqui");
            Ejemplar ejemplarPersistido = this.repositorioEjemplar.save(ejemplar);
            return ResponseEntity.ok().body(ejemplarPersistido);
        }
        return ResponseEntity.badRequest().build();
    }

    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/ejemplarForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ejemplar> addEjemplarForm(@RequestParam Integer id,
                                              @RequestParam String isbn,
                                              @RequestParam String estado){
        Ejemplar ejemplar = new Ejemplar();

        this.repositorioEjemplar.save(ejemplar);
        return ResponseEntity.created(null).body(ejemplar);
    }

    //POST con Form normal y fichero, se trabajará con JSONs normalmente...
    @PostMapping(value = "/ejemplarFormFichero", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ejemplar> addEjemplarFormFichero(@RequestParam String isbn,
                                              @RequestParam Integer id,
                                              @RequestParam String estado,
                                                     @RequestParam MultipartFile imagen){
        //Datos básicos del ejemplar
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setId(id);
        ejemplar.setEstado(estado);

        //guardado en la bbdd del ejemplar
        this.repositorioEjemplar.save(ejemplar);

        //devolución del objeto en formato json para el cliente
        return ResponseEntity.created(null).body(ejemplar);
    }

    //PUT --> UPDATE
    //falta actualizar ficheros
    @PutMapping("/{id}")
    public ResponseEntity<Ejemplar> updateEjemplar(@RequestBody Ejemplar ejemplar, @PathVariable Integer id){
        Ejemplar ejemplarPersistido = repositorioEjemplar.save(ejemplar);
        return ResponseEntity.ok().body(ejemplarPersistido);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteEjemplar(@PathVariable Long id){
        Ejemplar ejemplar = repositorioEjemplar.findById(id).orElse(null);
        repositorioEjemplar.deleteById(id);
        String mensaje = "ejemplar con id: "+id+" borrado";
        return ResponseEntity.ok().body(mensaje);
    }
}


