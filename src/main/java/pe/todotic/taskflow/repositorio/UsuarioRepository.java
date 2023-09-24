package pe.todotic.taskflow.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.todotic.taskflow.modelo.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer idNot);

    Optional<Usuario> findByEmail(String email);

}
