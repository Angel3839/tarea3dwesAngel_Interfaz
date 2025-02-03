 package com.Angelvf3839.tarea3dwesangel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.Angelvf3839.tarea3dwesangel.repositorios")
@ComponentScan(basePackages = {"com.Angelvf3839.tarea3dwesangel", "vista"})
public class Tarea3dwesangelApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Tarea3dwesangelApplication.class, args);
	}

}
