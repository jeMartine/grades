package edu.javeriana.taller3.aspecto;

import edu.javeriana.taller3.modelo.Nota;
import edu.javeriana.taller3.servicio.ServicioNota;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;


@Aspect
@Component
public class AspectoNota {

    @Autowired
    private ServicioNota notaService;

    @Before("execution(* edu.javeriana.taller3.servicio.ServicioNota.saveNota(..))")
    public void validarPorcentajesAcumulados(JoinPoint joinPoint) {
        Nota nota = (Nota) joinPoint.getArgs()[0];
        Integer estudianteId = nota.getEstudianteId();

        notaService.findNotasByEstudianteId(estudianteId)
                .collectList()
                .flatMap(notas -> {
                    double totalPorcentaje = notas.stream().mapToDouble(Nota::getPorcentaje).sum() + nota.getPorcentaje();
                    if (totalPorcentaje > 100) {
                        return Mono.error(new IllegalArgumentException("El porcentaje acumulado de las notas no puede superar el 100%"));
                    }
                    return Mono.just(true);
                })
                .block(); // Necesitamos bloquear para que el aspecto pueda detener la ejecución si hay un error.
    }
}
