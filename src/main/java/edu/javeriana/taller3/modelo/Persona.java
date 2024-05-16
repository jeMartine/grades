package edu.javeriana.taller3.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("persona")
public class Persona {

    @Id
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private char rol;

}

