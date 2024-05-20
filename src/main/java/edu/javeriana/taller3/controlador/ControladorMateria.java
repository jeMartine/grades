package edu.javeriana.taller3.controlador;

import edu.javeriana.taller3.modelo.Materia;
import edu.javeriana.taller3.repositorio.RepositorioMateria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.monitor.Monitor;

@RestController
@RequestMapping("/api/v1/materias")
public class ControladorMateria {
    @Autowired
    private RepositorioMateria repositorioMateria;

    @GetMapping("/all")
    Flux<Materia> getAllMaterias(){
        return repositorioMateria.findAll();
    }

    @PostMapping("/add")
    Mono<Materia> addMateria(@RequestBody Materia materia){
        return repositorioMateria.save(materia);
    }
    //retornar una materia por id
    @GetMapping("/{id}")
    Mono<ResponseEntity<Materia>> getMateriaId(@PathVariable Integer id) {
        return repositorioMateria.findById(id)
                .map(materia -> ResponseEntity.ok().body(materia))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota")));
    }

    //eliminar una materia por id
    @DeleteMapping("/delete/{id}")
    Mono<ResponseEntity<Void>> deleteMateriaById(@PathVariable Integer id){
        return repositorioMateria.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return repositorioMateria.deleteById(id)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la materia con el ID proporcionado"));
                    }
                });
    }

    //actualizar datos de una materia
    @PutMapping("/update/{id}")
    Mono<ResponseEntity<Materia>> updateMateriaById(@PathVariable Integer id, @RequestBody Materia materia){
        return repositorioMateria.findById(id)
                .flatMap(existingMateria->{
                    existingMateria.setNombre(materia.getNombre());
                    existingMateria.setCreditos(materia.getCreditos());
                    return repositorioMateria.save(existingMateria);
                })
                .map(updatedMateria -> ResponseEntity.ok(updatedMateria))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la nota")));
    }
}
