package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Curso;
import edu.javeriana.taller3.modelo.Nota;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositorioNota extends R2dbcRepository<Nota, Integer> {
    @Query("SELECT * FROM nota WHERE estudiante_id = :estudianteId")
    Flux<Nota> findByEstudianteId(Integer estudianteId);
}
