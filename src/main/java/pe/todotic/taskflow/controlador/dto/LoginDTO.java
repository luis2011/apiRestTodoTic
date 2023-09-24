package pe.todotic.taskflow.controlador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Login", description = "Recibe las credenciales para obtener un access token.")
@Data
public class LoginDTO {
    @Schema(example = "admin@gmail.com")
    private String email;

    @Schema(example = "admin")
    private String clave;
}
