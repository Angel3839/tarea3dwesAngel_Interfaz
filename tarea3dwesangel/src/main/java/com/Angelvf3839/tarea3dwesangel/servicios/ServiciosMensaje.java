package com.Angelvf3839.tarea3dwesangel.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;
import com.Angelvf3839.tarea3dwesangel.repositorios.MensajeRepository;

@Service
public class ServiciosMensaje {

    @Autowired
    private MensajeRepository mensajeRepo;

    public void insertar(Mensaje m) {
        mensajeRepo.save(m);
    }

    public Collection<Mensaje> verTodos() {
        return mensajeRepo.findAll();
    }

    public ArrayList<Mensaje> verMensajesRangoFechas(LocalDateTime primeraFecha, LocalDateTime segundaFecha) {
        List<Mensaje> mensajes = mensajeRepo.findMensajesBetweenFechas(primeraFecha, segundaFecha);
        return new ArrayList<>(mensajes);
    }
    
    public ArrayList<Mensaje> verMensajesPorPersona(long idPersona) {
        List<Mensaje> mensajes = mensajeRepo.buscarPorPersona(idPersona);
        return new ArrayList<>(mensajes);
    }

    public ArrayList<Mensaje> verMensajesPorCodigoPlanta(String codigoPlanta) {
        List<Mensaje> mensajes = mensajeRepo.buscarMensajesPorCodigoPlanta(codigoPlanta);
        return new ArrayList<>(mensajes);
    }

    public ArrayList<Mensaje> verMensajesPorEjemplar(long idEjemplar) {
        List<Mensaje> mensajes = mensajeRepo.mensajesPorEjemplar(idEjemplar);
        return new ArrayList<>(mensajes);
    }

    public boolean validarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            System.out.println("El mensaje está vacio.");
            return false;
        }
        if (mensaje.length() > 500) {
            System.out.println("El mensaje es muy largo. (Máximo 500 palabras)");
            return false;
        }
        return true;
    }
   
}