package com.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The MoviesApplication class serves as the entry point
 * for the Spring Boot application.
 */
@SpringBootApplication
public class MoviesApplication {

	/**
	 * The main method used to launch the application.
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}
}
