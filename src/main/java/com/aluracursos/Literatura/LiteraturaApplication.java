package com.aluracursos.Literatura;

import com.aluracursos.Literatura.main.Main;
import com.aluracursos.Literatura.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class LiteraturaApplication {

	@Autowired
	private BooksRepository booksRepository;

	public static void main(String[] args) {

		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			try {
				Main menu = new Main(booksRepository);
				menu.run();
			} catch (Exception e) {
				// Manejo de excepciones
				System.err.println("Error al ejecutar la aplicación: " + e.getMessage());
				e.printStackTrace();
			}
		};

	}
}
