package pe.todotic.taskflow.controlador;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.modelo.Usuario;

@Tag(name = "hola", description = "Endpoints para saludar.")
@RestController
public class HolaController {


    @RequestMapping(value = "/saludar", method = RequestMethod.GET)
    String saludar(@RequestParam(required = false) String nombres, @RequestParam(required = false) String apellidos) {
        if (nombres == null && apellidos == null) {
            return "Hola mundo!";
        } else if (nombres != null && apellidos == null) {
            return "Hola " + nombres + "!";
        } else {
            return "Hola " + nombres + " " + apellidos + "!";
        }
    }

    @RequestMapping(value = "/tareas/crear", method = RequestMethod.POST)
    Tarea crearTarea(@RequestBody Tarea tarea) {
        return tarea;
    }

    @RequestMapping(value = "/usuarios/crear", method = RequestMethod.POST)
    Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuario;
    }

}
