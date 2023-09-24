package pe.todotic.taskflow.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.taskflow.exception.BadRequestException;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.modelo.Usuario;
import pe.todotic.taskflow.repositorio.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "auth", description = "Endpoints para la gestión de usuarios.")
@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/paginar")
    Page<Usuario> paginar(
            @PageableDefault(size = 5, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return usuarioRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    Usuario obtener(@PathVariable Integer id) {
        return usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Usuario crear(@RequestBody @Validated Usuario usuario) {
        boolean emailYaEstaRegistrado = usuarioRepository.existsByEmail(usuario.getEmail());

        if (emailYaEstaRegistrado) {
            throw new BadRequestException("El email ya fue registrado.");
        }
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    Usuario actualizar(
            @PathVariable Integer id,
            @RequestBody @Validated Usuario form
    ) {
        boolean emailYaEstaRegistrado = usuarioRepository.existsByEmailAndIdNot(form.getEmail(), id);

        if (emailYaEstaRegistrado) {
            throw new BadRequestException("El email ya fue registrado.");
        }

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        usuario.setNombre(form.getNombre());
        usuario.setEmail(form.getEmail());
        usuario.setClave(passwordEncoder.encode(form.getClave()));
        usuario.setRol(form.getRol());

        return usuarioRepository.save(usuario);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioRepository.delete(usuario);
    }

    @GetMapping("/{idUsuario}/tareas")
    List<Tarea> obtenerTareasUsuario(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntityNotFoundException::new);
        return usuario.getTareas();
    }

}
