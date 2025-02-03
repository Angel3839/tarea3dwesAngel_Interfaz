package com.Angelvf3839.tarea3dwesangel.modelo;

public class Sesion {

    private String usuarioAutenticado;
    private Long idsuarioAutenticado;
    private Perfil perfilusuarioAutenticado;
    
    
	public Sesion(long i, String string, Perfil perfil) {
		 usuarioAutenticado = string;
		    idsuarioAutenticado =i;
		   perfilusuarioAutenticado = perfil;
	}
	public String getUsuarioAutenticado() {
		return usuarioAutenticado;
	}
	public void setUsuarioAutenticado(String usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
	public Long getIdsuarioAutenticado() {
		return idsuarioAutenticado;
	}
	public void setIdsuarioAutenticado(Long idsuarioAutenticado) {
		this.idsuarioAutenticado = idsuarioAutenticado;
	}
	public Perfil getPerfilusuarioAutenticado() {
		return perfilusuarioAutenticado;
	}
	public void setPerfilusuarioAutenticado(Perfil perfilusuarioAutenticado) {
		this.perfilusuarioAutenticado = perfilusuarioAutenticado;
	}
    
	
    
}
