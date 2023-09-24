package pe.todotic.taskflow.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.repositorio.TareaRepository;

import java.security.Principal;
import java.util.List;

@Tag(name = "cuenta", description = "Endpoints para la gestión de la cuenta.")
@RestController
@AllArgsConstructor
@RequestMapping("/api/cuenta")
public class CuentaController {

    private final TareaRepository tareaRepository;

    @GetMapping("/tareas")
    List<Tarea> obtenerTareas(Principal principal) {
        String emailUsuario = principal.getName();
        return tareaRepository.findByResponsableEmail(emailUsuario);
    }

    @PutMapping("/tareas/{idTarea}/finalizar")
    Tarea finalizarTarea(@PathVariable Integer idTarea, Principal principal) {
        String emailUsuario = principal.getName();

        Tarea tarea = tareaRepository
                .findByIdAndResponsableEmail(idTarea, emailUsuario)
                .orElseThrow(EntityNotFoundException::new);

        tarea.setEstado(Tarea.Estado.FINALIZADO);
        return tareaRepository.save(tarea);
    }

}
