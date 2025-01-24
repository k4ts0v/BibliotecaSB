package com.lvg.bibliotecasb.controlador;

import com.lvg.bibliotecasb.modelo.dto.Usuario;
import com.lvg.bibliotecasb.modelo.repositories.UsuariosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllerMOCK {
    UsuariosRepository usuariosRepository;

    // Constructor vacío del controlador.
    public UsuarioControllerMOCK() {}

    // Constructor del controlador.
    @Autowired
    public UsuarioControllerMOCK(UsuariosRepository usersRepository) { this.usuariosRepository = usersRepository; }

    // Método que comprueba si el usuario existe en la base de datos.
    public boolean exists(Usuario usuario) {
        boolean resultado;
        return resultado = getUsuario(usuario.getId()) != null;
    }

    // GET. Hace SELECT * en la bbdd.
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        List<Usuario> userList = usuariosRepository.findAll();
        System.out.println(userList);
        return ResponseEntity.ok(userList);
    }

    // GET con id. Hace SELECT por ID en la bbdd.
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario u = this.usuariosRepository.findUsuarioById(id);
        return ResponseEntity.ok(u);
    }

    // POST. Hace INSERT en la bbdd.
    @PostMapping("/usuario")
    public ResponseEntity<Usuario> addUser(@Valid @RequestBody Usuario usuario) {
        Usuario user = this.usuariosRepository.save(usuario);
        return ResponseEntity.created(null).body(user);
    }

    // POST con formulario y ficheros. Permite la entrada de datos desde un fichero o un formulario.
    @PostMapping(value = "/usuarioForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuario> addUser(@RequestParam Integer id,
                                           @RequestParam String dni,
                                           @RequestParam String nombre,
                                           @RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String tipo,
                                           @RequestParam LocalDate penalizacionHasta) {
        Usuario user = new Usuario(id, dni, nombre, email, password, tipo, penalizacionHasta);
        this.usuariosRepository.save(user);
        return ResponseEntity.created(null).body(user);
    }

//    PUT. Hace UPDATE en la bbdd.
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuario, @PathVariable Integer id) {
        Usuario user = this.usuariosRepository.findUsuarioById(id);
        usuariosRepository.save(usuario);
        return ResponseEntity.ok(user);
    }

//    DELETE por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Usuario usuario = usuariosRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuariosRepository.delete(usuario);
            return ResponseEntity.ok().body(String.format("Usuario %s eliminado.", usuario));
        }
        return ResponseEntity.badRequest().build();
    }

}
