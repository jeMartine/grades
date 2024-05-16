package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Curso;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RepositorioCurso extends R2dbcRepository<Curso, Integer> {
    @Query("SELECT * FROM curso WHERE materia_id = :materiaId AND profesor_id = :profesorId AND numero = numero")
    Mono<Curso> findByMateriaProfesorNumero(Integer materiaId, Integer profesorId, String numero);
}
