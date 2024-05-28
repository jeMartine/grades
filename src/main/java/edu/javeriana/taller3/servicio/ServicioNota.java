package edu.javeriana.taller3.servicio;

import edu.javeriana.taller3.modelo.Nota;
import edu.javeriana.taller3.repositorio.RepositorioNota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioNota {


    @Autowired
    private RepositorioNota repositorioNota;

    public Flux<Nota> findNotasByEstudianteId(Integer estudianteId) {
        return repositorioNota.findByEstudianteId(estudianteId);
    }

    public Mono<Nota> saveNota(Nota nota) {
        return repositorioNota.save(nota);
    }

    public Mono<Void> deleteNota(Integer id) {
        return repositorioNota.deleteById(id);
    }

    public Mono<Double> calcularPromedioPonderado(Integer estudianteId) {
        return repositorioNota.findByEstudianteId(estudianteId)
                .collectList()
                .map(notas -> {
                    double totalPonderado = notas.stream()
                            .mapToDouble(n -> n.getValor() * n.getPorcentaje() / 100).sum();
                    double totalPorcentaje = notas.stream()
                            .mapToDouble(Nota::getPorcentaje).sum();
                    return totalPorcentaje > 0 ? totalPonderado / totalPorcentaje * 100 : 0;
                });
    }

    public Mono<Nota> findById(Integer id) {
        return repositorioNota.findById(id);
    }

}
