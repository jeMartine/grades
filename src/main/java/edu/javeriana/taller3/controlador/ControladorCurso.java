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
    public Mono<Curso> addCurso(@RequestBody Curso curso) {
        return repositorioCurso.save(curso);
    }


    @GetMapping("/get/{materiaid}/{profesorid}/{numero}")
    public Mono<ResponseEntity<Curso>> getCurso(@PathVariable Integer materiaid, @PathVariable Integer profesorid, @PathVariable String numero){
        return repositorioCurso.findByMateriaProfesorNumero(materiaid, profesorid, numero)
                .map(curso->ResponseEntity.ok().body(curso))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //código para el manejo de errores

}
