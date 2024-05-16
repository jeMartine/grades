package edu.javeriana.taller3.controlador;

import edu.javeriana.taller3.modelo.Curso;
import edu.javeriana.taller3.modelo.Persona;
import edu.javeriana.taller3.repositorio.RepositorioCurso;
import edu.javeriana.taller3.repositorio.RepositorioMateria;
import edu.javeriana.taller3.repositorio.RepositorioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class ControladorCurso {
    @Autowired
    RepositorioCurso repositorioCurso;

    @Autowired
    RepositorioMateria repositorioMateria;

    @Autowired
    RepositorioPersona repositorioPersona;

    //leer todos los cursos
    @GetMapping("/all")
    Flux<Curso> getAllCursos(){
        return (repositorioCurso.findAll());
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<String>> addCurso(@RequestBody Curso curso) {

        // Verificar si existen los IDs de materia, profesor y estudiante
    Mono<Boolean> materiaExists = repositorioMateria.existsById(curso.getMateriaId());
    //Mono<Boolean> profesorExists = repositorioPersona.findProfesorById(curso.getProfesorId(), 'P');
    //Mono<Boolean> estudianteExists = repositorioPersona.findEstudianteById(curso.getEstudianteId(), 'E');

    // Suscribirse a los Monos y manejar los resultados
        return materiaExists.flatMap(materiaExistsResult -> {
        if (!materiaExistsResult) {
            return Mono.just(ResponseEntity.badRequest().body("La materia especificada no existe."));
        } else {
            return profesorExists.flatMap(profesorExistsResult -> {
                if (!profesorExistsResult) {
                    return Mono.just(ResponseEntity.badRequest().body("El profesor especificado no existe."));
                } else {
                    return estudianteExists.map(estudianteExistsResult -> {
                        if (!estudianteExistsResult) {
                            return ResponseEntity.badRequest().body("El estudiante especificado no existe.");
                        } else {
                            try {
                                repositorioCurso.save(curso);
                                return ResponseEntity.ok("El curso se ha insertado correctamente.");
                            } catch (Exception e) {
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error al insertar el curso: " + e.getMessage());
                            }
                        }
                    });
                }
            });
        }
    });
}



    Mono<ResponseEntity<Persona>> updatePersonaById(@PathVariable Integer id, @RequestBody Persona persona){
        return repositorioPersona.findById(id)
                .flatMap(existingPersona->{
                    existingPersona.setNombre(persona.getNombre());
                    existingPersona.setApellido(persona.getApellido());
                    existingPersona.setCorreo(persona.getCorreo());
                    existingPersona.setRol(persona.getRol());
                    return repositorioPersona.save(existingPersona);
                })
                .map(updatedPersona -> ResponseEntity.ok(updatedPersona))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }





    @GetMapping("/get/{materiaid}/{profesorid}/{numero}")
    public Mono<ResponseEntity<Curso>> getCurso(@PathVariable Integer materiaid, @PathVariable Integer profesorid, @PathVariable String numero){
        return repositorioCurso.findByMateriaProfesorNumero(materiaid, profesorid, numero)
                .map(curso->ResponseEntity.ok().body(curso))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //código para el manejo de errores

}
