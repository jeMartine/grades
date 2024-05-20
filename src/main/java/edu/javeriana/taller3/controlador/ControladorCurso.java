package edu.javeriana.taller3.controlador;

import edu.javeriana.taller3.modelo.Curso;
import edu.javeriana.taller3.repositorio.RepositorioCurso;
import edu.javeriana.taller3.repositorio.RepositorioMateria;
import edu.javeriana.taller3.repositorio.RepositorioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
    public Mono<Curso> addCurso(@RequestBody Curso curso) {
        return repositorioCurso.save(curso);
    }

    @GetMapping("/{materiaid}/{profesorid}/{numero}")
    public Mono<ResponseEntity<Curso>> getCurso(@PathVariable Integer materiaid, @PathVariable Integer profesorid, @PathVariable String numero){
        return repositorioCurso.findByMateriaProfesorNumero(materiaid, profesorid, numero)
                .map(curso -> ResponseEntity.ok().body(curso))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontró el curso")));
    }


    @DeleteMapping("/delete/{materiaid}/{profesorid}/{numero}")
    public Mono<ResponseEntity<Void>> deleteCurso(@PathVariable Integer materiaid, @PathVariable Integer profesorid, @PathVariable String numero){
        return repositorioCurso.existCurso(materiaid, profesorid, numero)
                .flatMap(exists -> {
                    if (exists) {
                        return repositorioCurso.deleteCursoByProfesor(materiaid, profesorid, numero)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota con el ID proporcionado"));
                    }
                });
    }


    //actualizar datos de un curso
    @PutMapping("/update")
    public Mono<ResponseEntity<Object>> updateCurso(@RequestBody Curso curso){
        return repositorioCurso.updateCurso(
                curso.getMateriaId(),
                curso.getProfesorId(),
                curso.getNumero(),
                curso.getEstudianteId(),
                curso.getFechaInicio(),
                curso.getFechaFin()
        ).flatMap(rowsUpdated -> {
            if (rowsUpdated != null && rowsUpdated > 0) {
                return Mono.just(ResponseEntity.noContent().build());
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            }
        });
    }

}
