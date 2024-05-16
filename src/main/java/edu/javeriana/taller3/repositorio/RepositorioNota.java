package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Nota;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RepositorioNota extends R2dbcRepository<Nota, Integer> {
}
