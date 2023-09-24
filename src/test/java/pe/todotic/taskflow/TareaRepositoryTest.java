package pe.todotic.taskflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pe.todotic.taskflow.modelo.Tarea;
import pe.todotic.taskflow.repositorio.TareaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;

//    @Transactional
//    @Rollback
    @Test
    void testCreate() {
        // crear una nueva instancia de tarea
        Tarea tarea = new Tarea();
        tarea.setTitulo("Tarea de prueba");
        tarea.setDescripcion("Esta es suna tarea de prueba");
        tarea.setEstado(Tarea.Estado.CREADO);
        tarea.setPrioridad(3);
        tarea.setFechaLimite(LocalDate.now().plusDays(1));
        tarea.setFechaCreacion(LocalDateTime.now());

        Tarea tareaGuardada =  tareaRepository.save(tarea);

        assertNotNull(tareaGuardada.getId());
        assertEquals(tarea.getTitulo(), tareaGuardada.getTitulo());
        assertEquals(tarea.getDescripcion(), tareaGuardada.getDescripcion());
        assertEquals(tarea.getEstado(), tareaGuardada.getEstado());
        assertEquals(tarea.getPrioridad(), tareaGuardada.getPrioridad());
        assertEquals(tarea.getFechaLimite(), tareaGuardada.getFechaLimite());
        assertEquals(tarea.getFechaCreacion(), tareaGuardada.getFechaCreacion());

        assertSame(tarea, tareaGuardada);
    }

    @Test
    void testFindById() {
        Tarea tareaEncontrada = tareaRepository.findById(1).orElse(null);

        assertThat(tareaEncontrada).isNotNull();
        assertThat(tareaEncontrada.getId()).isEqualTo(1);
    }

    @Test
    void testFindAll() {
        List<Tarea> tareas = tareaRepository.findAll();
        assertThat(tareas.size()).isEqualTo(5);
    }

    @Test
    void testFindById_returnNull() {
        Tarea tareaEncontrada = tareaRepository.findById(6).orElse(null);
        assertThat(tareaEncontrada).isNull();
    }

    @Test
    void testDelete() {
        Tarea tareaEncontrada = tareaRepository.findById(1).orElseThrow(RuntimeException::new);

        tareaRepository.delete(tareaEncontrada);

        assertThat(tareaRepository.findById(1)).isEmpty();
    }

    @Test
    void testFindByResponsableEmail() {
        String emailUsuario3 = "usuario3@gmail.com";
        List<Tarea> tareasUsuario3 = tareaRepository.findByResponsableEmail(emailUsuario3);
        assertThat(tareasUsuario3.size()).isEqualTo(2);

        String emailAdmin = "admin@gmail.com";
        List<Tarea> tareasAdmin = tareaRepository.findByResponsableEmail(emailAdmin);
        assertThat(tareasAdmin.size()).isEqualTo(0);
    }

}
