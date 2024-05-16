package edu.javeriana.taller3.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("curso")
public class Curso {

    @Column("materia_id")
    private Integer materiaId;

    @Column("profesor_id")
    private Integer profesorId;

    private String numero;
    private Integer estudianteId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;


}
