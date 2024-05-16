package edu.javeriana.taller3.controlador;

import edu.javeriana.taller3.modelo.Persona;
import edu.javeriana.taller3.repositorio.RepositorioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/personas")
public class ControladorPersona {

    @Autowired
    private RepositorioPersona repositorioPersona;

    //agregar persona
    @PostMapping("/add")
    Mono<Persona> addPersona(@RequestBody Persona persona) {
        return repositorioPersona.save(persona);
    }

    //retornar todas las personas
    @GetMapping("/all")
    Flux<Persona> getAllPersonas() {
        return repositorioPersona.findAll();
    }

    //retornar una persona por id
    @GetMapping("/{id}")
    Mono<ResponseEntity<Persona>> getPersonaId(@PathVariable Integer id) {
        return repositorioPersona.findById(id)
                .map(persona -> ResponseEntity.ok().body(persona))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //eliminar una persona por id
    @DeleteMapping("/delete/{id}")
    Mono<ResponseEntity<Void>> deletePersonaById(@PathVariable Integer id){
        return repositorioPersona.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //actualizar datos de una persona
    @PutMapping("/update/{id}")
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

}
