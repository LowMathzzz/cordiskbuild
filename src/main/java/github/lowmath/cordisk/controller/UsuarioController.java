package github.lowmath.cordisk.controller;

import github.lowmath.cordisk.model.Usuario;
import github.lowmath.cordisk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariodata;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuariodata.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable String id) {
        return usuariodata.findById(id).orElse(null);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuariodata.save(usuario);
    }

    @GetMapping("check")
    public ResponseEntity<?> check(@RequestBody UserLogin user) {
        Usuario usuario = usuariodata.findByEmail(user.username()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest.build();
        }
        return ResponseEntity.ok();
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable String id, @RequestBody Usuario usuarioDetails) {
        Usuario usuario = usuariodata.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setPassword(usuarioDetails.getPassword());
            return usuariodata.save(usuario);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable String id) {
        usuariodata.deleteById(id);
    }
}

public record UserLogin(string username, string password);
