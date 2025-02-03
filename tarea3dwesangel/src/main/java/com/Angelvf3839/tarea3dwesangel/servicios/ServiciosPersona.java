package com.Angelvf3839.tarea3dwesangel.servicios;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.repositorios.PersonaRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosPersona {

    @Autowired
    private PersonaRepository personaRepo;

    public void insertar(Persona pers) {
        personaRepo.save(pers);
    }

    public Collection<Persona> verTodos() {
        return personaRepo.findAll();
    }

    public boolean emailExistente(String email) {
        return personaRepo.existsByEmail(email);
    }

    public long idUsuarioAutenticado(String usuario) {
        Long idPersona = personaRepo.obtenerIdDeUsuario(usuario);
        return (idPersona != null) ? idPersona : -1;
    }
    
    public boolean validarPersona(Persona pers) {
        if (pers == null) {
            return false;
        }
        if (pers.getNombre() == null || pers.getNombre().isEmpty()) {
            return false;
        }
        if (pers.getNombre().length() < 3 || pers.getNombre().length() > 20) {
            return false;
        }
        if (pers.getEmail() == null || pers.getEmail().isEmpty()) {
            return false;
        }
        if (pers.getEmail().length() < 5 || !pers.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || pers.getEmail().length() > 40) {
            return false;
        }
        return true;
    }

    public Persona buscarPorNombre(String nombre){
    	return personaRepo.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Transactional
    @Modifying
    public boolean eliminarPersona(Long id) {
        try {
            if (personaRepo.existsById(id)) {
                personaRepo.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la persona: " + e.getMessage());
            return false;
        }
    }
}