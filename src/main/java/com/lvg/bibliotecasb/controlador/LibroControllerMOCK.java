package com.lvg.bibliotecasb.controlador;

import com.lvg.bibliotecasb.modelo.dto.Libro;
import com.lvg.bibliotecasb.modelo.dto.Usuario;
import com.lvg.bibliotecasb.modelo.repositories.LibrosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/libros")

public class LibroControllerMOCK {
    static LibrosRepository repositorioLibros;

    public LibroControllerMOCK(){}

    @Autowired
    public LibroControllerMOCK(LibrosRepository repositorioLibros){
        this.repositorioLibros = repositorioLibros;
    }

    public boolean exists(Libro libro) {
        boolean resultado;
        return resultado = getLibro(libro.getIsbn()) != null;
    }

    //GET --> SELECT *
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Libro>> getLibro(){
        List<Libro> lista = new ArrayList<>(this.repositorioLibros.findAll()); // Copia inmutable
        //System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    //GET BY ISBN --> SELECT BY ISBN
    @GetMapping("/{isbn}")
    //@Cacheable
    @Transactional(readOnly = true)
    public ResponseEntity<Libro> getLibro(@PathVariable String isbn){
            Libro l = repositorioLibros.findLibroByIsbn(isbn);
            return ResponseEntity.ok(l);
    }

   //POST --> INSERT
    @PostMapping("/libro")
    public ResponseEntity<Libro> addLibro(@Valid @RequestBody Libro libro){
        System.out.println("Entra aqui");
        Libro libroPersistido = this.repositorioLibros.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //POST con Form normal, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroForm(@Valid @RequestParam String isbn,
                                              @RequestParam String titulo,
                                              @RequestParam String autor){
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        this.repositorioLibros.save(libro);
        return ResponseEntity.created(null).body(libro);
    }

    //POST con Form normal y fichero, se trabajará con JSONs normalmente...
    @PostMapping(value = "/libroFormFichero", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Libro> addLibroFormFichero(@Valid @RequestParam String isbn,
                                              @RequestParam String titulo,
                                              @RequestParam String autor,
                                                     @RequestParam MultipartFile imagen){
        //Datos básicos del libro
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        //guardado en la bbdd del libro
        this.repositorioLibros.save(libro);

        //devolución del objeto en formato json para el cliente
        return ResponseEntity.created(null).body(libro);
    }

    //PUT --> UPDATE
    //falta actualizar ficheros
    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> updateLibro(@Valid @RequestBody Libro libro, @PathVariable String isbn){
        Libro libroPersistido = repositorioLibros.save(libro);
        return ResponseEntity.ok().body(libroPersistido);
    }

    //DELETE
    @DeleteMapping("/{isbn}")
    @Transactional
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn){
            repositorioLibros.deleteLibroByIsbn(isbn);
            String mensaje = "libro con isbn: "+isbn+" borrado";
            return ResponseEntity.ok().body(mensaje);
        }
    }


