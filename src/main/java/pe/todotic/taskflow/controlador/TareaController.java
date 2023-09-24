package pe.todotic.taskflow.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.modelo.Usuario;
import pe.todotic.taskflow.repositorio.TareaRepository;
import pe.todotic.taskflow.repositorio.UsuarioRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "tareas", description = "Endpoints para la gestión de un tareas.")
@RestController
@RequestMapping("/api/tareas")
@AllArgsConstructor
@Slf4j
public class TareaController {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/paginar")
    public Page<Tarea> paginar(
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return tareaRepository.findAll(pageable);
    }

    @GetMapping
    public List<Tarea> listar(Principal principal) {
        log.info("usuario actual: {}", principal.getName());
        return tareaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tarea obtener(@PathVariable Integer id) {
        return tareaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Tarea crear(@Validated @RequestBody Tarea tarea) {
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setEstado(Tarea.Estado.CREADO);

        return tareaRepository.save(tarea);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public Tarea actualizar(@PathVariable Integer id, @Validated @RequestBody Tarea form) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        tarea.setTitulo(form.getTitulo());
        tarea.setDescripcion(form.getDescripcion());
        tarea.setPrioridad(form.getPrioridad());
        tarea.setFechaLimite(form.getFechaLimite());

        return tareaRepository.save(tarea);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        tareaRepository.delete(tarea);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idTarea}/asignar-responsable/{idUsuario}")
    public Tarea asignarReponsable(@PathVariable Integer idTarea, @PathVariable Integer idUsuario) {
        Tarea tarea = tareaRepository.findById(idTarea).orElseThrow(EntityNotFoundException::new);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(EntityNotFoundException::new);

        tarea.setResponsable(usuario);
        tarea.setEstado(Tarea.Estado.ASIGNADO);

        return tareaRepository.save(tarea);
    }
}
