package com.Angelvf3839.tarea3dwesangel.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Angelvf3839.tarea3dwesangel.modelo.Credenciales;
import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.repositorios.CredencialesRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.PersonaRepository;

	
	@Service
	public class ServiciosCredenciales {
		@Autowired
		CredencialesRepository credencialesRepo;
		
		@Autowired
		PersonaRepository personaRepo;
	
	public boolean autenticar(String usuario, String password) {
	    return credencialesRepo.verificarCredenciales(usuario, password);
	    }
	
	public boolean usuarioExistente(String usuario) {
        return credencialesRepo.existsByUsuario(usuario);
    }
	
	public void insertar(String usuario, String password, Long idPersona) {
        Persona p = personaRepo.findById(idPersona).orElse(null);
        Credenciales credenciales = new Credenciales();
        credenciales.setUsuario(usuario);
        credenciales.setPassword(password);
        credenciales.setPersona(p);
        credencialesRepo.save(credenciales);
    }
	
	/* Método para validar una contraseña */
	
	public boolean validarContraseña(String contraseña) {
		if (contraseña.matches("^(?=.*[.,])[A-Za-z0-9.,]{8,}$")) {
			return true;
		}
		return false;
	}
}