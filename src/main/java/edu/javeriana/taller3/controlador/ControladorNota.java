package edu.javeriana.taller3.controlador;

import edu.javeriana.taller3.modelo.Nota;
import edu.javeriana.taller3.modelo.Persona;
import edu.javeriana.taller3.repositorio.RepositorioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/notas")
public class ControladorNota {
    @Autowired
    private RepositorioNota repositorioNota;

    @GetMapping("/all")
    Flux<Nota> getAllNotas(){
        return repositorioNota.findAll();
    }

    @PostMapping("/add")
    Mono<Nota> addNota(@RequestBody Nota nota){
        return repositorioNota.save(nota);
    }

    //retornar una persona por id
    @GetMapping("/{id}")
    Mono<ResponseEntity<Nota>> getCursoId(@PathVariable Integer id) {
        return repositorioNota.findById(id)
                .map(nota -> ResponseEntity.ok().body(nota))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //eliminar una persona por id
    @DeleteMapping("/delete/{id}")
    Mono<ResponseEntity<Void>> deleteNotaById(@PathVariable Integer id){
        return repositorioNota.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //actualizar datos de una persona
    @PutMapping("/update/{id}")
    Mono<ResponseEntity<Nota>> updatePersonaById(@PathVariable Integer id, @RequestBody Nota nota){
        return repositorioNota.findById(id)
                .flatMap(existingNota->{
                    existingNota.setMateriaId(nota.getMateriaId());
                    existingNota.setProfesorId(nota.getProfesorId());
                    existingNota.setEstudianteId(nota.getEstudianteId());
                    existingNota.setObservacion(nota.getObservacion());
                    existingNota.setValor(nota.getValor());
                    existingNota.setPorcentaje(nota.getPorcentaje());
                    return repositorioNota.save(existingNota);
                })
                .map(updatedNota -> ResponseEntity.ok(updatedNota))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
