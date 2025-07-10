package com.simpsons;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpsonsCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpsonsCrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {
			System.out.println("SimpsonsCrudApplication arrancó correctamente y detecta los controladores.");
		};
	}


}


