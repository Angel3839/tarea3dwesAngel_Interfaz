package com.Angelvf3839.tarea3dwesangel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import vista.FachadaInvitado;

public class Principal implements CommandLineRunner {
	
	
	
	
	 @Autowired
	 private FachadaInvitado fachadaInvitado;

	@Override
	public void run(String... args) throws Exception {
		fachadaInvitado.menuInvitado();
    }
}