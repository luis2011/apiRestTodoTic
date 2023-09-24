package pe.todotic.taskflow.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 100)
    private String titulo;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    @Max(5)
    @Min(1)
    private Integer prioridad;

    @NotNull
    @FutureOrPresent
    private LocalDate fechaLimite;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "fecha_crea")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "responsable_id", referencedColumnName = "id")
    private Usuario responsable;

    public enum Estado {
        CREADO, // = 0
        ASIGNADO, // 1
        FINALIZADO // 2
    }
}
