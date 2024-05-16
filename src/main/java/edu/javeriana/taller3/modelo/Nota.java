
package edu.javeriana.taller3.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("nota")
public class Nota {

    @Id
    private Integer id;

    @Column("materia_id")
    private Integer materiaId;

    @Column("profesor_id")
    private Integer profesorId;

    @Column("estudiante_id")
    private Integer estudianteId;

    private String observacion;
    private float valor;
    private float porcentaje;


    // Constructor, getters y setters
}
