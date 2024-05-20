package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Curso;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import java.time.LocalDate;


public interface RepositorioCurso extends R2dbcRepository<Curso, Integer> {
    @Query("SELECT * FROM curso WHERE materia_id = :materiaId AND profesor_id = :profesorId AND numero = :numero")
    Mono<Curso> findByMateriaProfesorNumero(Integer materiaId, Integer profesorId, String numero);

    @Query("SELECT * FROM curso WHERE materia_id = :materiaId AND profesor_id = :profesorId AND numero = :numero")
    Mono<Boolean> existCurso(Integer materiaId, Integer profesorId, String numero);
    @Query("DELETE FROM curso WHERE materia_id = :materiaId AND profesor_id = :profesorId AND numero = :numero")
    Mono<Void> deleteCursoByProfesor(Integer materiaId, Integer profesorId, String numero);

    @Query("UPDATE curso SET materia_id = :materiaId, profesor_id = :profesorId, numero = :numero, estudiante_id = :estudianteId, fecha_inicio = :fechaInicio, fecha_fin = :fechaFin WHERE materia_id = :materiaId AND profesor_id = :profesorId AND numero = :numero")
    Mono<Integer> updateCurso(Integer materiaId, Integer profesorId, String numero, Integer estudianteId, LocalDate fechaInicio, LocalDate fechaFin);

}
