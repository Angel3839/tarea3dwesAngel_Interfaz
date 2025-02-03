package com.Angelvf3839.tarea3dwesangel.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Angelvf3839.tarea3dwesangel.modelo.Perfil;
import com.Angelvf3839.tarea3dwesangel.modelo.Sesion;

@Service
public class Controlador {

	private Sesion usuarioAutenticado = new Sesion(-1L, "invitado", Perfil.INVITADO);

	public Sesion getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Sesion usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}

	public void cerrarSesion() {
		this.usuarioAutenticado = new Sesion(-1L, "invitado", Perfil.INVITADO);
	}

}