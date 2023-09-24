package pe.todotic.taskflow.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.todotic.taskflow.modelo.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    List<Tarea> findByResponsableEmail(String emailResponsable);

    Optional<Tarea> findByIdAndResponsableEmail(Integer id, String emailResponsable);

}
