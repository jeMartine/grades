package edu.javeriana.taller3.repositorio;

import edu.javeriana.taller3.modelo.Persona;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RepositorioPersona extends R2dbcRepository<Persona, Integer> {

    @Query("SELECT * FROM persona WHERE id = :id AND rol = :rol")
    Mono<Persona> findProfesorById (Integer id, char rol);

    @Query("SELECT * FROM persona WHERE id = :id AND rol = :rol")
    Mono<Persona> findEstudianteById (Integer id, char rol);
}
