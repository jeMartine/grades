package edu.javeriana.taller3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@SpringBootApplication
public class Taller3Application {

	public static void main(String[] args) {
		SpringApplication.run(Taller3Application.class, args);
	}

}
