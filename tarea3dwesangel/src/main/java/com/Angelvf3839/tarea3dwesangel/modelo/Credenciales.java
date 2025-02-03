package com.Angelvf3839.tarea3dwesangel.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
	@Table(name="credenciales")
	public class Credenciales implements Serializable{
	
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
		
		@Column(unique=true)
		private String usuario;
		
		@Column
		private String password;
		
		@OneToOne
		@JoinColumn(name="idPersona", unique=true)
		private Persona persona;
		
		public Credenciales() {
		}

		public Credenciales(String usuario, String password) {
			this.usuario = usuario;
			this.password = password;
		}

		public Credenciales(String usuario, String password, Persona persona) {
			this.usuario = usuario;
			this.password = password;
			this.persona = persona;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Persona getPersona() {
			return persona;
		}

		public void setPersona(Persona persona) {
			this.persona = persona;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, password, usuario);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Credenciales other = (Credenciales) obj;
			return Objects.equals(id, other.id) && Objects.equals(password, other.password)
					&& Objects.equals(usuario, other.usuario);
		}

		@Override
		public String toString() {
			return "Credenciales [id=" + id + ", usuario=" + usuario + ", password=" + password + "]";
		}

	}