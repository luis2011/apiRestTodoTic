package pe.todotic.taskflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pe.todotic.taskflow.modelo.Tarea;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static pe.todotic.taskflow.TestUtils.asJsonString;

@AutoConfigureMockMvc
@SpringBootTest
public class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Test
    void listarTareas_isOk() throws Exception {
        String tokenAdmin = authService.obtenerBearerToken("admin@gmail.com", "12345");
        String tokenNormal = authService.obtenerBearerToken("usuario2@gmail.com", "12345");

        mockMvc.perform(
                        get("/api/tareas")
                                .header(HttpHeaders.AUTHORIZATION, tokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));

        mockMvc.perform(
                        get("/api/tareas")
                                .header(HttpHeaders.AUTHORIZATION, tokenNormal)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Transactional
    @Rollback
    @Test
    void crearTarea_isOk() throws Exception {
        String tokenAdmin = authService.obtenerBearerToken("admin@gmail.com", "12345");
        String tokenNormal = authService.obtenerBearerToken("usuario2@gmail.com", "12345");

        Tarea tarea = new Tarea();
        tarea.setTitulo("Tarea de prueba");
        tarea.setDescripcion("Esta es suna tarea de prueba");
        tarea.setPrioridad(3);
        tarea.setFechaLimite(LocalDate.now().plusDays(1));

        mockMvc.perform(
                        post("/api/tareas")
                                .header(HttpHeaders.AUTHORIZATION, tokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(tarea))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.titulo", is(tarea.getTitulo())));

        mockMvc.perform(
                        post("/api/tareas")
                                .header(HttpHeaders.AUTHORIZATION, tokenNormal)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(tarea))
                )
                .andExpect(status().isForbidden());
    }

    @Transactional
    @Rollback
    @Test
    void finalizarTarea_isOk() throws Exception {
        String tokenAdmin = authService.obtenerBearerToken("admin@gmail.com", "12345");
        String tokenNormal = authService.obtenerBearerToken("usuario2@gmail.com", "12345");

        mockMvc.perform(
                        put("/api/tareas/1/asignar-responsable/1")
                                .header(HttpHeaders.AUTHORIZATION, tokenAdmin)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.responsable.id", is(1)))
                .andExpect(jsonPath("$.estado", is(Tarea.Estado.ASIGNADO.name())));

        mockMvc.perform(
                        put("/api/tareas/1/asignar-responsable/1")
                                .header(HttpHeaders.AUTHORIZATION, tokenNormal)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}
