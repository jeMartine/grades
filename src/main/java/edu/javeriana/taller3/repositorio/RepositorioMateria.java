package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Materia;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RepositorioMateria extends R2dbcRepository<Materia, Integer> {
}
