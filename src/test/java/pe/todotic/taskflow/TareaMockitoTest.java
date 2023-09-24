package pe.todotic.taskflow;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.todotic.taskflow.controlador.TareaController;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.repositorio.TareaRepository;
import pe.todotic.taskflow.repositorio.UsuarioRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareaMockitoTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TareaController tareaController;

    private Tarea tarea;

    @BeforeEach
    void setup() {
        tarea = new Tarea();
        tarea.setId(1);
        tarea.setTitulo("Tarea de prueba");
        tarea.setDescripcion("Esta es suna tarea de prueba");
        tarea.setEstado(Tarea.Estado.CREADO);
        tarea.setPrioridad(3);
        tarea.setFechaLimite(LocalDate.now().plusDays(1));
        tarea.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void testFindById_en_tareaRepository() {
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));

        Optional<Tarea> resultado = tareaRepository.findById(1);

        assertTrue(resultado.isPresent());
        assertSame(resultado.get(), tarea);

        verify(tareaRepository, times(1)).findById(1);
    }

    @Test
    void testObtener_en_tareaController() {
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));

        Tarea resultado = tareaController.obtener(1);

        assertNotNull(resultado);
        assertSame(tarea, resultado);

        assertThrows(EntityNotFoundException.class, () -> tareaController.obtener(2));

        verify(tareaRepository, times(2)).findById(anyInt());
    }
}
